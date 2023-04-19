package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.UserDto;
import com.commerceplatform.api.accounts.dtos.mappers.UserMapper;
import com.commerceplatform.api.accounts.enums.RoleEnum;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.exceptions.ValidationException;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.models.jpa.UserModel;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import com.commerceplatform.api.accounts.services.rules.UserServiceRules;
import com.commerceplatform.api.accounts.utils.Validators;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService extends Validators implements UserServiceRules {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        super();
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public UserDto create(UserDto input) throws BadRequestException{
        super.nonNull("id", input.getId(), "attribute id most be null");
        super.isRequired("email", input.getEmail(), "attribute name is required");
        super.isValidEmail("email", input.getEmail(), "attribute email is not valid");
        super.isRequired("username", input.getUsername(), "attribute username is required");
        super.isRequired("password", input.getPassword(), "attribute password is required");
        super.hasMin("password", input.getPassword(), 4, "minimum size must be 8");

        if(Boolean.FALSE.equals(super.validate())) {
            Map<String, List<String>> errors = new HashMap<>(super.getAllErrors());
            super.clearErrors();
            throw new ValidationException(errors);
        }

        var existsEmail = userRepository.findByEmail(input.getEmail());
        if (existsEmail.isPresent())
            throw new BadRequestException("Email already registered");

        var newUser = UserMapper.mapper(input);
        newUser.setPassword(passwordEncoder.encode(input.getPassword()));

        var roles = new ArrayList<RoleModel>();
        var defaultRole = new RoleModel(null, RoleEnum.USER.getName(), "Default user");

        roles.add(defaultRole);
        newUser.setRoles(roles);
        newUser.setCreatedAt(LocalDate.now());

        var result = userRepository.save(newUser);

        return UserDto.builder()
            .id(result.getId())
            .email(result.getEmail())
            .username(result.getUsername())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .roles(result.getRoles())
            .build();
    }

    @Cacheable(value = "user")
    public List<UserModel> findAll() {
        return this.userRepository.findAll();
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email not found"));
    }
}
