package com.commerceplatform.api.accounts.repositories.jpa;

import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    @Query("SELECT r FROM RoleModel r WHERE LOWER(r.name) = LOWER(?1)")
    Optional<List<RoleModel>> findByName(String name);
}
