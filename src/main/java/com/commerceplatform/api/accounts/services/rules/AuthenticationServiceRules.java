package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.dtos.LoginDTO;
import com.commerceplatform.api.accounts.outputs.LoginOutput;

public interface AuthenticationServiceRules {
    LoginOutput login(LoginDTO loginDTO);
}
