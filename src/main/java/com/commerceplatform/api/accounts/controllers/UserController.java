package com.commerceplatform.api.accounts.controllers;

import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.models.jpa.UserModel;
import com.commerceplatform.api.accounts.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserModel> create(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDTO));
    }

    @PostMapping("/recovery-code")
    public ResponseEntity<?> sendRecoveryCode(@RequestBody Object email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.sendRecoveryCode("email@gmail.com"));
    }

    @GetMapping
    public String teste() {
        return "teste";
    }
}
