package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.controllers.outputs.CreateUserOutput;
import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.models.jpa.UserModel;

import java.util.List;

public interface UserServiceRules {
    CreateUserOutput create (UserDTO userDTO);
    List<UserModel> findAll();
}
