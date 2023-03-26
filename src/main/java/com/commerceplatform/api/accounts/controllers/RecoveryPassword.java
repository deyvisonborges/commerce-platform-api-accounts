package com.commerceplatform.api.accounts.controllers;

import com.commerceplatform.api.accounts.dtos.redis.UserRecoveryCodeDto;
import com.commerceplatform.api.accounts.services.RecoveryPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recovery-password")
public class RecoveryPassword {

    private final RecoveryPasswordService recoveryPasswordService;

    public RecoveryPassword(RecoveryPasswordService recoveryPasswordService) {
        this.recoveryPasswordService = recoveryPasswordService;
    }

    @PostMapping("/email")
    public ResponseEntity<Void> sendRecoveryCode(@RequestBody UserRecoveryCodeDto request) {
        recoveryPasswordService.sendRecoveryCode(request.email());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
