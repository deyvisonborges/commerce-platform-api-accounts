package com.commerceplatform.api.accounts.dtos.mappers;

import com.commerceplatform.api.accounts.dtos.UserDto;
import com.commerceplatform.api.accounts.models.jpa.UserModel;

public class UserMapper {
    private UserMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static UserModel mapper(UserDto userDto) {
        return UserModel.builder()
            .id(userDto.getId())
            .username(userDto.getUsername())
            .email(userDto.getEmail())
            .password(userDto.getPassword())
            .createdAt(userDto.getCreatedAt())
            .updatedAt(userDto.getUpdatedAt())
            .roles(userDto.getRoles())
            .build();
    }
}
