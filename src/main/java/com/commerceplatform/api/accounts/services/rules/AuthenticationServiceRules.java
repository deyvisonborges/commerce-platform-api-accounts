package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.dtos.LoginDTO;

public interface AuthenticationServiceRules {
    String login(LoginDTO loginDTO);
}
