package com.commerceplatform.api.accounts.services.rules;

import com.commerceplatform.api.accounts.dtos.RecoveryPasswordDto;

public interface RecoveryPasswordRules {
    void sendRecoveryCode (String email);
    boolean recoveryCodeIsValid(String code, String email);
    void updatePasswordByRecoveryCode(RecoveryPasswordDto input);
}
