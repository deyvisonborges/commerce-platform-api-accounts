package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.UserDto;
import com.commerceplatform.api.accounts.dtos.mappers.UserMapper;
import com.commerceplatform.api.accounts.enums.RoleEnum;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.models.jpa.UserModel;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import com.commerceplatform.api.accounts.services.rules.UserServiceRules;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserServiceRules {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public UserDto create(UserDto input) {
        try {
            if(Objects.nonNull(input.getId())) {
                throw new BadRequestException("Id most be null");
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
        } catch (Exception e) {
            throw new BadRequestException("Failed to create user: "+e.getMessage());
        }
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
