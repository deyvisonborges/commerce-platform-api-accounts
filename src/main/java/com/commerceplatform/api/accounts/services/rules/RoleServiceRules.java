package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.dtos.inputs.CreateRoleInput;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;

public interface RoleServiceRules {
    RoleModel create(CreateRoleInput input);
}
