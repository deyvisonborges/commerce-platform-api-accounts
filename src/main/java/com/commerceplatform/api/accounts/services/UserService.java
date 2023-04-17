package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.dtos.mappers.UserMapper;
import com.commerceplatform.api.accounts.enums.utils.RoleEnumUtil;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.models.jpa.UserModel;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UsersRolesService usersRolesService;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UsersRolesService usersRolesService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.usersRolesService = usersRolesService;
    }


    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public UserModel create(UserDTO request) {
        try {
            if(Objects.nonNull(request.getId())) {
                throw new BadRequestException("Id most be null");
            }

            var existsEmail = userRepository.findByEmail(request.getEmail());
            if (existsEmail.isPresent())
                throw new BadRequestException("Email already registered");

            var newUser = UserMapper.mapper(request);
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));

            var roles = new ArrayList<RoleModel>();

            // TODO: Resolve o retorno do valor do enum para o convencional
            var defaultRole = new RoleModel(null, RoleEnumUtil.getName("USER").getName(), "Default user");
            roles.add(defaultRole);

            newUser.setRoles(roles);

            return  userRepository.save(newUser);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create user: "+e.getMessage());
        }
    }

    public List<UserModel> findAll() {
        return this.userRepository.findAll();
    }
}
