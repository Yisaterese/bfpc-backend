package com.bfpc.mapper;

import com.bfpc.domain.entity.User;
import com.bfpc.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-04T02:34:56+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.roles( rolesToStringSet( user.getRoles() ) );
        userDto.userType( userTypeToString( user.getUserType() ) );
        userDto.id( user.getId() );
        userDto.firstName( user.getFirstName() );
        userDto.lastName( user.getLastName() );
        userDto.email( user.getEmail() );
        userDto.password( user.getPassword() );
        userDto.phoneNumber( user.getPhoneNumber() );
        userDto.profileImageUrl( user.getProfileImageUrl() );
        userDto.address( user.getAddress() );
        userDto.localGovernmentArea( user.getLocalGovernmentArea() );
        userDto.preferredLanguage( user.getPreferredLanguage() );
        userDto.enabled( user.isEnabled() );
        userDto.createdAt( user.getCreatedAt() );
        userDto.updatedAt( user.getUpdatedAt() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userType( stringToUserType( userDto.getUserType() ) );
        user.id( userDto.getId() );
        user.firstName( userDto.getFirstName() );
        user.lastName( userDto.getLastName() );
        user.email( userDto.getEmail() );
        user.password( userDto.getPassword() );
        user.phoneNumber( userDto.getPhoneNumber() );
        user.profileImageUrl( userDto.getProfileImageUrl() );
        user.address( userDto.getAddress() );
        user.localGovernmentArea( userDto.getLocalGovernmentArea() );
        user.enabled( userDto.isEnabled() );
        user.preferredLanguage( userDto.getPreferredLanguage() );
        user.createdAt( userDto.getCreatedAt() );
        user.updatedAt( userDto.getUpdatedAt() );

        return user.build();
    }

    @Override
    public List<UserDto> toDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
