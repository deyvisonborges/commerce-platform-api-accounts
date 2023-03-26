package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.models.jpa.UserTypeModel;

import java.util.List;

public interface UserTypeRules {
    List<UserTypeModel> findAll();
}
