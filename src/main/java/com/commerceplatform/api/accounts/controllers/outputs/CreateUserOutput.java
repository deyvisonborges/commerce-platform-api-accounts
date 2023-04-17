package com.commerceplatform.api.accounts.controllers.outputs;

import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserOutput {
    private Long id;
    private String email;
    private String username;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<RoleModel> roles;
}