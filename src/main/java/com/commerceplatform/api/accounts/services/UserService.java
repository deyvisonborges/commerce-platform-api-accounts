package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.dtos.mappers.UserMapper;
import com.commerceplatform.api.accounts.models.UserModel;
import com.commerceplatform.api.accounts.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserDTO userDto) {
        try {
            if(Objects.isNull(userDto.getId())) {
                throw new RuntimeException("Id deve ser nulo");
            }

            var newUser = UserMapper.mapper(userDto);
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar o usuário");
        }
    }
}
