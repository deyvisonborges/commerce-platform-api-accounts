package com.commerceplatform.api.accounts.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt;
    private Long userTypeId;
}
