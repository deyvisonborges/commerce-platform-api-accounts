package com.commerceplatform.api.accounts.controllers;

import com.commerceplatform.api.accounts.dtos.RecoveryPasswordDto;
import com.commerceplatform.api.accounts.services.RecoveryPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recovery-password")
public class RecoveryPassword {
    private final RecoveryPasswordService recoveryPasswordService;

    public RecoveryPassword(RecoveryPasswordService recoveryPasswordService) {
        this.recoveryPasswordService = recoveryPasswordService;
    }

    @PostMapping("/email")
    public ResponseEntity<Void> sendRecoveryCode(@RequestBody RecoveryPasswordDto input) {
        recoveryPasswordService.sendRecoveryCode(input.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/pin")
    public ResponseEntity<Boolean> recoveryCodeIsValid(
            @RequestParam("code") String code,
            @RequestParam("email") String email
        ) {
        return ResponseEntity.status(HttpStatus.OK).body(recoveryPasswordService.recoveryCodeIsValid(code, email));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePasswordByRecoveryCode(@RequestBody RecoveryPasswordDto input) {
        recoveryPasswordService.updatePasswordByRecoveryCode(input);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
