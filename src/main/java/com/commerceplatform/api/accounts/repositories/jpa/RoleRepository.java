package com.commerceplatform.api.accounts.repositories.jpa;

import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {

//    @Query("SELECT r FROM RoleModel r WHERE r.name = ?1")
//    RoleModel findByName(String name);

    @Query("SELECT r FROM RoleModel r WHERE LOWER(r.name) = LOWER(?1)")
    List<RoleModel> findByName(String name);
}
