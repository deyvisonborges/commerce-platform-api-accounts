package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.LoginDTO;
import com.commerceplatform.api.accounts.dtos.TokenDTO;
import com.commerceplatform.api.accounts.services.rules.AuthenticationServiceRules;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationServiceRules {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            TokenService tokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String login(LoginDTO loginDTO) {
        var requestedUser = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        Authentication auth = authenticationManager.authenticate(requestedUser);
        String token = tokenService.generateToken(auth);

        return new TokenDTO(token, "Bearer").token();
    }


}
