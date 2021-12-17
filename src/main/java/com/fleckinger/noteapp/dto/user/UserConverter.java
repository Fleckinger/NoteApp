package com.fleckinger.noteapp.dto.user;

import com.fleckinger.noteapp.entity.user.User;

/**
 * Utility class to convert User entity to DTO and vice versa
 */
public class UserConverter {

    public static User dtoToEntity(UserDto dto) {
        User user = new User();

        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }

    public static UserDto entityToDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());

        return dto;
    }
}
