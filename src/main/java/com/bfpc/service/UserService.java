package com.bfpc.service;

import com.bfpc.domain.entity.Role;
import com.bfpc.domain.entity.User;
import com.bfpc.domain.repository.RoleRepository;
import com.bfpc.domain.repository.UserRepository;
import com.bfpc.dto.UserDto;
import com.bfpc.exception.ResourceNotFoundException;
import com.bfpc.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service for user management operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * Create a new user.
     *
     * @param userDto the user data transfer object
     * @return the created user DTO
     */
    @Transactional
    public UserDto createUser(UserDto userDto) {
        // Check if user already exists
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already in use");
        }

        // Map DTO to entity
        User user = userMapper.toEntity(userDto);

        // Encode password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Set default account settings
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);

        // Assign roles
        Set<Role> roles = new HashSet<>();
        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            userDto.getRoles().forEach(roleName -> {
                Role role = roleRepository.findByName(Role.RoleName.valueOf(roleName))
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));
                roles.add(role);
            });
        } else {
            // Assign default role based on user type
            Role defaultRole;
            switch (user.getUserType()) {
                case FARMER:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_FARMER)
                            .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
                    break;
                case BUYER:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_BUYER)
                            .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
                    break;
                case ADMIN:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
                    break;
                case EXTENSION_OFFICER:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_EXTENSION_OFFICER)
                            .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
                    break;
                case NGO_PARTNER:
                case GOVERNMENT_PARTNER:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_NGO_PARTNER)
                            .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid user type");
            }
            roles.add(defaultRole);
        }
        user.setRoles(roles);

        // Save user
        User savedUser = userRepository.save(user);

        // Map entity to DTO and return
        return userMapper.toDto(savedUser);
    }

    /**
     * Get a user by ID.
     *
     * @param id the user ID
     * @return the user DTO
     */
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    /**
     * Get a user by email.
     *
     * @param email the user email
     * @return the user DTO
     */
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userMapper.toDto(user);
    }

    /**
     * Get all users.
     *
     * @return a list of all user DTOs
     */
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    /**
     * Update a user.
     *
     * @param id the user ID
     * @param userDto the updated user data
     * @return the updated user DTO
     */
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Check if email is being changed and if it's already in use
        if (!existingUser.getEmail().equals(userDto.getEmail()) && 
                userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Check if phone number is being changed and if it's already in use
        if (!existingUser.getPhoneNumber().equals(userDto.getPhoneNumber()) && 
                userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already in use");
        }

        // Update user fields
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        existingUser.setAddress(userDto.getAddress());
        existingUser.setLocalGovernmentArea(userDto.getLocalGovernmentArea());
        existingUser.setProfileImageUrl(userDto.getProfileImageUrl());
        existingUser.setPreferredLanguage(userDto.getPreferredLanguage());

        // Update password if provided
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Save updated user
        User updatedUser = userRepository.save(existingUser);

        // Map entity to DTO and return
        return userMapper.toDto(updatedUser);
    }

    /**
     * Delete a user by ID.
     *
     * @param id the user ID
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Change a user's password.
     *
     * @param id the user ID
     * @param currentPassword the current password
     * @param newPassword the new password
     * @return the updated user DTO
     */
    @Transactional
    public UserDto changePassword(Long id, String currentPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }
}