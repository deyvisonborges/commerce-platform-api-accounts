package com.commerceplatform.api.accounts.repositories.jpa;

import com.commerceplatform.api.accounts.models.jpa.UserTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeModel, Long> {
}
