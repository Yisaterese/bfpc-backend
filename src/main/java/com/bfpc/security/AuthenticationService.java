package com.bfpc.security;

import com.bfpc.domain.entity.Role;
import com.bfpc.domain.entity.User;
import com.bfpc.domain.repository.RoleRepository;
import com.bfpc.domain.repository.UserRepository;
import com.bfpc.dto.AuthenticationRequest;
import com.bfpc.dto.AuthenticationResponse;
import com.bfpc.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Service for authentication operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user.
     *
     * @param request the registration request
     * @return the authentication response with JWT tokens
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already in use");
        }

        // Assign roles
        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            request.getRoles().forEach(roleName -> {
                Role role = roleRepository.findByName(Role.RoleName.valueOf(roleName))
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
                roles.add(role);
            });
        } else {
            // Assign default role based on user type
            Role defaultRole;
            switch (request.getUserType()) {
                case FARMER:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_FARMER)
                            .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
                    break;
                case BUYER:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_BUYER)
                            .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
                    break;
                case ADMIN:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
                    break;
                case EXTENSION_OFFICER:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_EXTENSION_OFFICER)
                            .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
                    break;
                case NGO_PARTNER:
                case GOVERNMENT_PARTNER:
                    defaultRole = roleRepository.findByName(Role.RoleName.ROLE_NGO_PARTNER)
                            .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid user type");
            }
            roles.add(defaultRole);
        }

        // Create user
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(request.getUserType())
                .address(request.getAddress())
                .localGovernmentArea(request.getLocalGovernmentArea())
                .preferredLanguage(request.getPreferredLanguage())
                .roles(roles)
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();

        // Save user
        userRepository.save(user);

        // Generate tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType().name())
                .build();
    }

    /**
     * Authenticate a user.
     *
     * @param request the authentication request
     * @return the authentication response with JWT tokens
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType().name())
                .build();
    }
}