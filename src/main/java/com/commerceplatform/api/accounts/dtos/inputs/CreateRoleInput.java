package com.commerceplatform.api.accounts.dtos.inputs;

import com.commerceplatform.api.accounts.enums.RoleEnum;

public record CreateRoleInput(RoleEnum name, String description) {
}
