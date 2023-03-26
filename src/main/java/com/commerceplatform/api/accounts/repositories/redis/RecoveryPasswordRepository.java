package com.commerceplatform.api.accounts.repositories.redis;

import com.commerceplatform.api.accounts.models.redis.RecoveryPasswordModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecoveryPasswordRepository extends CrudRepository<RecoveryPasswordModel, String> {
    Optional<RecoveryPasswordModel> findByEmail(String email);
}
