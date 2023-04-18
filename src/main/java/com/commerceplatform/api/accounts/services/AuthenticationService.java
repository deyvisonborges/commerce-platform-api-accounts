package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.LoginDTO;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.ValidationException;
import com.commerceplatform.api.accounts.outputs.LoginOutput;
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

    public LoginOutput login(LoginDTO request) {

        super.isRequired("email", request.email(), "attribute email is required");
        super.isRequired("password", request.password(), "attribute password is required");
        super.hasMin("password", request.password(), 4, "minimum size must be 4");

        if(Boolean.FALSE.equals(super.validate())) {
            Map<String, List<String>> errors = new HashMap<>(super.getAllErrors());
            super.clearErrors();
            throw new ValidationException(errors);
        }

        if (!super.isValidEmail(request.email())) {
            throw new BadRequestException("Attribute 'email' is not valid");
        }

        var auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

        var user = userService.findByEmail(request.email());
        var token = jwtService.generateToken(auth, user.getId());
        var expiresAt = jwtService.getExpirationDate(token);

        return LoginOutput.builder()
            .accessToken(token)
            .expiresAt(expiresAt.toString())
            .build();
    }


}
