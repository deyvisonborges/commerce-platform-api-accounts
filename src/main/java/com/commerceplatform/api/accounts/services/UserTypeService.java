package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.models.jpa.UserTypeModel;
import com.commerceplatform.api.accounts.repositories.jpa.UserTypeRepository;
import com.commerceplatform.api.accounts.services.rules.UserTypeRules;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService implements UserTypeRules {
    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    @Cacheable(value = "user_type")
    public List<UserTypeModel> findAll() {
        return this.userTypeRepository.findAll();
    }
}
