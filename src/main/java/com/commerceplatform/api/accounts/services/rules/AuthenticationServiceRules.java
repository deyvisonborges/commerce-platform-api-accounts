package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.dtos.inputs.LoginInput;
import com.commerceplatform.api.accounts.dtos.outputs.LoginOutput;

public interface AuthenticationServiceRules {
    LoginOutput login(LoginInput loginInput);
}
