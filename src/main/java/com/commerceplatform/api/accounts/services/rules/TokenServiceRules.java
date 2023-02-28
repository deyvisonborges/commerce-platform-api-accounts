package com.commerceplatform.api.accounts.services.rules;

import org.springframework.security.core.Authentication;

public interface TokenServiceRules {
    String generateToken(Authentication auth);
    Boolean isValid(String token);
    Long getUserId(String token);
}
