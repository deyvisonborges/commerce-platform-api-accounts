package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.services.records.RoleRecord;

public class RoleRecordMapper {
    private RoleRecordMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static RoleModel mapper(RoleRecord role) {
        return RoleModel.builder()
            .id(role.getId())
            .name(role.getName().toString())
            .description(role.getDescription())
            .build();
    }
}
