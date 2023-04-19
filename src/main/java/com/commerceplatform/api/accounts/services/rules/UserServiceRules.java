package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.dtos.UserDto;
import com.commerceplatform.api.accounts.models.jpa.UserModel;

import java.util.List;

public interface UserServiceRules {
    UserDto create (UserDto userDTO);
    List<UserModel> findAll();
}
