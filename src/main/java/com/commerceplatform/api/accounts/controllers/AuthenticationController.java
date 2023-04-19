package com.commerceplatform.api.accounts.controllers;

import com.commerceplatform.api.accounts.dtos.inputs.LoginInput;
import com.commerceplatform.api.accounts.dtos.outputs.LoginOutput;
import com.commerceplatform.api.accounts.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutput> login(@RequestBody LoginInput loginInput) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(loginInput));
    }
}
