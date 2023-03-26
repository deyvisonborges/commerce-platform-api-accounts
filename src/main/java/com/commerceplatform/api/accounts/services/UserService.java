package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.dtos.mappers.UserMapper;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.models.jpa.UserModel;
import com.commerceplatform.api.accounts.models.redis.RecoveryCode;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import com.commerceplatform.api.accounts.repositories.jpa.UserTypeRepository;
import com.commerceplatform.api.accounts.repositories.redis.RecoveryCodeRepository;
import com.commerceplatform.api.accounts.services.rules.UserServiceRules;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class UserService implements UserServiceRules {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final RecoveryCodeRepository recoveryCodeRepository;

    public UserService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            UserTypeRepository userTypeRepository,
            RecoveryCodeRepository recoveryCodeRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.recoveryCodeRepository = recoveryCodeRepository;
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    // limpa todos os caches dessa referência, no nosso caso, o user
    public UserModel create(UserDTO request) {
        try {
            if(Objects.nonNull(request.getId())) {
                throw new BadRequestException("Id most be null");
            }

            var userTypeOpt = userTypeRepository.findById(request.getUserTypeId());

            if (userTypeOpt.isEmpty()) {
                throw new NotFoundException("userTypId not found");
            }

            var existsEmail = userRepository.findByEmail(request.getEmail());
            if (existsEmail.isPresent())
                throw new BadRequestException("Email already registered");

            var userType = userTypeOpt.get();
            var newUser = UserMapper.mapper(request, userType);
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));

            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create user: "+e.getMessage());
        }
    }

    @Override
    public Object sendRecoveryCode(Object email) {
        String code = String.format("%04d", new Random().nextInt(10000));

        var recoveryCode = new RecoveryCode();
        recoveryCode.setCode(code);

        recoveryCodeRepository.save(recoveryCode);
        return null;
    }

}
