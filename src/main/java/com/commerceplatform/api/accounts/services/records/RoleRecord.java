package com.commerceplatform.api.accounts.services.records;

import com.commerceplatform.api.accounts.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRecord {
    private Long id;
    private String name;
    private String description;
}
