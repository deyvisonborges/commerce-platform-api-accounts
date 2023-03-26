package com.commerceplatform.api.accounts.repositories.redis;

import com.commerceplatform.api.accounts.models.redis.RecoveryCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryCodeRepository extends CrudRepository<RecoveryCode, String> {
}
