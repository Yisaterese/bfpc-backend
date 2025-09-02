package com.bfpc.mapper;

import com.bfpc.domain.entity.Role;
import com.bfpc.domain.entity.User;
import com.bfpc.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for converting between User entity and UserDto.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert User entity to UserDto.
     *
     * @param user the user entity
     * @return the user DTO
     */
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToStringSet")
    @Mapping(source = "userType", target = "userType", qualifiedByName = "userTypeToString")
    UserDto toDto(User user);

    /**
     * Convert UserDto to User entity.
     *
     * @param userDto the user DTO
     * @return the user entity
     */
    @Mapping(target = "roles", ignore = true)
    @Mapping(source = "userType", target = "userType", qualifiedByName = "stringToUserType")
    User toEntity(UserDto userDto);

    /**
     * Convert a list of User entities to a list of UserDtos.
     *
     * @param users the list of user entities
     * @return the list of user DTOs
     */
    List<UserDto> toDtoList(List<User> users);

    /**
     * Convert a set of Role entities to a set of role name strings.
     *
     * @param roles the set of role entities
     * @return the set of role name strings
     */
    @Named("rolesToStringSet")
    default Set<String> rolesToStringSet(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }

    /**
     * Convert a User.UserType enum to a string.
     *
     * @param userType the user type enum
     * @return the user type string
     */
    @Named("userTypeToString")
    default String userTypeToString(User.UserType userType) {
        if (userType == null) {
            return null;
        }
        return userType.name();
    }

    /**
     * Convert a string to a User.UserType enum.
     *
     * @param userType the user type string
     * @return the user type enum
     */
    @Named("stringToUserType")
    default User.UserType stringToUserType(String userType) {
        if (userType == null) {
            return null;
        }
        return User.UserType.valueOf(userType);
    }
}