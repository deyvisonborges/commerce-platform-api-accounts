package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.dtos.LoginDTO;
import com.commerceplatform.api.accounts.dtos.TokenDTO;

public interface AuthenticationServiceRules {
    TokenDTO login(LoginDTO loginDTO);
}
