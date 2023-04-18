package com.commerceplatform.api.accounts.controllers;

import com.commerceplatform.api.accounts.dtos.UserDTO;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.models.jpa.UserModel;
import com.commerceplatform.api.accounts.repositories.jpa.RoleRepository;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("users-roles")
public class UsersRolesController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UsersRolesController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
}
