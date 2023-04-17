package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.inputs.CreateRoleInput;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.NotFoundException;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.repositories.jpa.RoleRepository;
import com.commerceplatform.api.accounts.services.records.RoleRecord;
import com.commerceplatform.api.accounts.services.rules.RoleServiceRules;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleService implements RoleServiceRules {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleModel create(CreateRoleInput input) {
        try {

            var existsRole = roleRepository.findByName(input.name().toString());

            if(Objects.nonNull(existsRole)) {
                throw new BadRequestException("Role has already been declared");
            }
            var role = new RoleRecord();
            role.setName(input.name().toString());
            role.setDescription(input.description());
            return roleRepository.save(RoleRecordMapper.mapper(role));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<RoleModel> findAll() {
        return this.roleRepository.findAll();
    }
}
