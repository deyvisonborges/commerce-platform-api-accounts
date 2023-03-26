package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.models.redis.RecoveryPasswordModel;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import com.commerceplatform.api.accounts.repositories.redis.RecoveryPasswordRepository;
import com.commerceplatform.api.accounts.services.rules.RecoveryPasswordRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RecoveryPasswordService implements RecoveryPasswordRules {
    private final RecoveryPasswordRepository recoveryPasswordRepository;
    private final UserRepository userRepository;

    public RecoveryPasswordService(RecoveryPasswordRepository recoveryPasswordRepository, UserRepository userRepository) {
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

        if (code.equals(userRecoveryCode.getCode())) return true;

        return false;
    }
}
