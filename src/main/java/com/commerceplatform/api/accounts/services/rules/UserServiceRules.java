package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.models.UserModel;

public interface UserServiceRules {
    UserModel create (UserDTO userDTO);
}
