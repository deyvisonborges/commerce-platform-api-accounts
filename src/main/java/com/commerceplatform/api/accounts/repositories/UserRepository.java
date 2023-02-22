package com.commerceplatform.api.accounts.repositories;

import com.commerceplatform.api.accounts.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
