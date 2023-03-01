package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.dtos.mappers.UserMapper;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.models.UserModel;
import com.commerceplatform.api.accounts.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel createUser(UserDTO userDto) {
        try {
            if(Objects.isNull(userDto.getId())) {
                throw new BadRequestException("Id must be null");
            }

            var existsEmail = userRepository.findByEmail(userDto.getEmail());
            if (existsEmail.isPresent())
                throw new BadRequestException("Email already registered");

            var newUser = UserMapper.mapper(userDto);
             newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create user: "+e.getMessage());
        }
    }
}
