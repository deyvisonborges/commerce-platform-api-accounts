package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.RecoveryPasswordDto;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.exceptions.ValidationException;
import com.commerceplatform.api.accounts.models.redis.RecoveryPasswordModel;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import com.commerceplatform.api.accounts.repositories.redis.RecoveryPasswordRepository;
import com.commerceplatform.api.accounts.services.rules.RecoveryPasswordRules;
import com.commerceplatform.api.accounts.utils.Validators;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class RecoveryPasswordService extends Validators implements RecoveryPasswordRules {
    @Value("${redis.recoverypassword.timeout}")
    private String recoveryPasswordTimeout;

    private final PasswordEncoder passwordEncoder;
    private final RecoveryPasswordRepository recoveryPasswordRepository;
    private final UserRepository userRepository;

    public RecoveryPasswordService(PasswordEncoder passwordEncoder, RecoveryPasswordRepository recoveryPasswordRepository, UserRepository userRepository) {
        super();
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
        super.isRequired("code", input.getCode(), "attribute code is required");
        super.hasLength("code", input.getCode(), 4);
        super.isRequired("email", input.getEmail(), "attribute email is required");
        super.isValidEmail("email", input.getEmail(), "attribute email is not valid");
        super.isRequired("password", input.getPassword(), "attribute password is required");

        if(Boolean.FALSE.equals(super.validate())) {
            Map<String, List<String>> errors = new HashMap<>(super.getAllErrors());
            super.clearErrors();
            throw new ValidationException(errors);
        }

        if(Boolean.FALSE.equals(recoveryCodeIsValid(input.getCode(), input.getEmail()))) {
            throw new BadRequestException("Expired recovery code");
        }

        var userOpt = userRepository.findByEmail(input.getEmail())
            .orElseThrow(
                () -> new NotFoundException("No user found with email " + input.getEmail())
            );

        userOpt.setPassword(passwordEncoder.encode(userOpt.getPassword()));
        userRepository.save(userOpt);
    }
}
