package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.dtos.RoleDto;
import com.commerceplatform.api.accounts.dtos.mappers.RoleMapper;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import com.commerceplatform.api.accounts.exceptions.ValidationException;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.repositories.jpa.RoleRepository;
import com.commerceplatform.api.accounts.services.rules.RoleServiceRules;
import com.commerceplatform.api.accounts.utils.Validators;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RoleService extends Validators implements RoleServiceRules {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        super();
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto create(RoleDto input) {
        super.nonNull("id", input.getId(), "attribute id most be null");
        super.isRequired("name", input.getName(), "attribute name is required");
        super.isRequired("description", input.getDescription(), "attribute description is required");

        if(Boolean.FALSE.equals(super.validate())) {
            Map<String, List<String>> errors = new HashMap<>(super.getAllErrors());
            super.clearErrors();
            throw new ValidationException(errors);
        }

        var existsRole = roleRepository.findByName(input.getName());

        if(Objects.nonNull(existsRole)) {
            throw new BadRequestException("Role has already been declared");
        }

        var role = new RoleDto();
        role.setName(input.getName());
        role.setDescription(input.getDescription());
        var result = roleRepository.save(RoleMapper.mapper(role));

        return RoleDto.builder()
            .name(result.getName())
            .description(result.getDescription())
            .build();
    }

    public List<RoleModel> findAll() {
        return this.roleRepository.findAll();
    }
}
