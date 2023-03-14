package com.commerceplatform.api.accounts.repositories;

import com.commerceplatform.api.accounts.models.UserTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeModel, Long> {
}
