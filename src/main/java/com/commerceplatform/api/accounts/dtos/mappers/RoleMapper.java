package com.commerceplatform.api.accounts.dtos.mappers;

import com.commerceplatform.api.accounts.dtos.RoleDto;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;

public class RoleMapper {
    private RoleMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static RoleModel mapper(RoleDto role) {
        return RoleModel.builder()
            .id(role.getId())
            .name(role.getName())
            .description(role.getDescription())
            .build();
    }
}
