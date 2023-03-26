package com.commerceplatform.api.accounts.services.rules;

public interface RecoveryPasswordRules {
    void sendRecoveryCode (String email);
    boolean recoveryCodeIsValid(String code, String email);
}
