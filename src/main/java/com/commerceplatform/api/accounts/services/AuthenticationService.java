package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.LoginDTO;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.models.UserModel;
import com.commerceplatform.api.accounts.repositories.UserRepository;
import com.commerceplatform.api.accounts.services.rules.AuthenticationServiceRules;
import com.commerceplatform.api.accounts.utils.Validators;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.commerceplatform.api.accounts.security.JwtService;

import java.util.Optional;

@Service
public class AuthenticationService implements AuthenticationServiceRules {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public String login(LoginDTO request) {
        if (!Validators.isValidEmail(request.email())) {
            throw new BadRequestException("Param validations: attribute email is not valid");
        }

        var requestedCredentials = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authenticationManager.authenticate(requestedCredentials);

        Optional<UserModel> userOpt = userRepository.findByEmail(request.email());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("user with email not found");
        }
        var user = userOpt.get();

        return jwtService.generateToken(auth, user.getId());
    }


}
