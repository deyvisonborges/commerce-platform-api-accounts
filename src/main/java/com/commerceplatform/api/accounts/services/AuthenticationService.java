package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.LoginDTO;
import com.commerceplatform.api.accounts.dtos.TokenDTO;
import com.commerceplatform.api.accounts.services.rules.AuthenticationServiceRules;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationServiceRules {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public TokenDTO login(LoginDTO loginDTO) {
        try {
            var requestedCredentials = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
            var auth = authenticationManager.authenticate(requestedCredentials);
            var token = jwtService.generateToken(auth);

            return new TokenDTO(token, "Bearer");
        } catch (Exception e) {
            throw new RuntimeException("Deu erro" + e.getMessage());
        }
    }


}
