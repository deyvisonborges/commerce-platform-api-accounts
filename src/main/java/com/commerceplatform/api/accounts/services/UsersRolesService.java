package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.models.jpa.UserModel;
import com.commerceplatform.api.accounts.repositories.jpa.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersRolesService {

    public final UserRepository userRepository;

    public UsersRolesService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveRoleInUser(UserModel user, RoleModel role) {
        List<RoleModel> roles = new ArrayList<RoleModel>();

        roles.add(role);
        user.setRoles(roles);

        this.userRepository.save(user);
    }

}
