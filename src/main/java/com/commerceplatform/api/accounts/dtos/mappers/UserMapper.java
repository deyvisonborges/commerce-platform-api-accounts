package com.commerceplatform.api.accounts.dtos.mappers;

import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.models.UserModel;

public class UserMapper {
    private UserMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static UserModel mapper(UserDTO userDTO) {
        return UserModel.builder()
            .id(userDTO.getId())
            .username(userDTO.getUsername())
            .email(userDTO.getEmail())
            .createdAt(userDTO.getCreatedAt())
            .updatedAt(userDTO.getUpdatedAt())
            .build();
    }
}
