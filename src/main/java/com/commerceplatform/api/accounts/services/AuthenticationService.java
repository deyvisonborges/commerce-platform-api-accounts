package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.inputs.LoginInput;
import com.commerceplatform.api.accounts.exceptions.ValidationException;
import com.commerceplatform.api.accounts.dtos.outputs.LoginOutput;
import com.commerceplatform.api.accounts.services.rules.AuthenticationServiceRules;
import com.commerceplatform.api.accounts.utils.Validators;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.commerceplatform.api.accounts.security.JwtService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AuthenticationService extends Validators implements AuthenticationServiceRules  {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public LoginOutput login(LoginInput input) {

        super.isRequired("email", input.email(), "attribute email is required");
        super.isValidEmail("email", input.email(), "attribute email is not valid");
        super.isRequired("password", input.password(), "attribute password is required");
        super.hasMin("password", input.password(), 4, "minimum size must be 4");

        if(Boolean.FALSE.equals(super.validate())) {
            Map<String, List<String>> errors = new HashMap<>(super.getAllErrors());
            super.clearErrors();
            throw new ValidationException(errors);
        }

        var auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.email(),
                input.password()
            )
        );

        var user = userService.findByEmail(input.email());
        var token = jwtService.generateToken(auth, user.getId());
        var expiresAt = jwtService.getExpirationDate(token);

        return LoginOutput.builder()
            .accessToken(token)
            .expiresAt(expiresAt.toString())
            .build();
    }
}
