package com.commerceplatform.api.accounts.dtos;

import javax.validation.constraints.NotBlank;

public record LoginDTO (
        @NotBlank(message = "atributo obrigatório")
        String email,

        @NotBlank(message = "atributo obrigatório")
        String password
) {

}
