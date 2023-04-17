package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.LoginDTO;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import com.commerceplatform.api.accounts.services.rules.AuthenticationServiceRules;
import com.commerceplatform.api.accounts.utils.Validators;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.commerceplatform.api.accounts.security.JwtService;

@Service
@Transactional
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
            throw new BadRequestException("Attribute 'email' is not valid");
        }

        var auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

        var user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new NotFoundException("User with email not found"));

        return jwtService.generateToken(auth, user.getId());
    }


}
