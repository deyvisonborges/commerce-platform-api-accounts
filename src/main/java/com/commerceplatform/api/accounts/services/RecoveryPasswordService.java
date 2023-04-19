package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.RecoveryPasswordDto;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.models.redis.RecoveryPasswordModel;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import com.commerceplatform.api.accounts.repositories.redis.RecoveryPasswordRepository;
import com.commerceplatform.api.accounts.services.rules.RecoveryPasswordRules;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RecoveryPasswordService implements RecoveryPasswordRules {
    @Value("${redis.recoverypassword.timeout}")
    private String recoveryPasswordTimeout;

    private final PasswordEncoder passwordEncoder;
    private final RecoveryPasswordRepository recoveryPasswordRepository;
    private final UserRepository userRepository;

    public RecoveryPasswordService(PasswordEncoder passwordEncoder, RecoveryPasswordRepository recoveryPasswordRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.recoveryPasswordRepository = recoveryPasswordRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void sendRecoveryCode(String email) {
        RecoveryPasswordModel recoveryPasswordModel;
        String code = String.format("%04d", new Random().nextInt(10000));

        var userRecoveryCodeOpt = recoveryPasswordRepository.findByEmail(email);

        if(userRecoveryCodeOpt.isEmpty()) {
            var user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new NotFoundException("User not found");
            }

            recoveryPasswordModel = new RecoveryPasswordModel();
            recoveryPasswordModel.setEmail(email);
        } else {
            recoveryPasswordModel = userRecoveryCodeOpt.get();
        }

        recoveryPasswordModel.setCode(code);
        recoveryPasswordModel.setCreatedAt(LocalDateTime.now());

        recoveryPasswordRepository.save(recoveryPasswordModel);
    }

    @Override
    public boolean recoveryCodeIsValid(String code, String email) {
        var userRecoveryCodeOpt = recoveryPasswordRepository.findByEmail(email);

        if(userRecoveryCodeOpt.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        var userRecoveryCode = userRecoveryCodeOpt.get();

        LocalDateTime timeout =  userRecoveryCode.getCreatedAt().plusMinutes(Long.parseLong(recoveryPasswordTimeout));
        LocalDateTime now = LocalDateTime.now();

        return code.equals(userRecoveryCode.getCode()) && now.isBefore(timeout);
    }

    @Override
    public void updatePasswordByRecoveryCode(RecoveryPasswordDto input) {
        if(recoveryCodeIsValid(input.getCode(), input.getEmail())) {
           var userOpt = userRepository.findByEmail(input.getEmail())
               .orElseThrow(
                   () -> new NotFoundException("No user found with email " + input.getEmail())
               );

            userOpt.setPassword(passwordEncoder.encode(userOpt.getPassword()));
           userRepository.save(userOpt);
           return;
        }
        throw new BadRequestException("Invalid params");
    }
}
