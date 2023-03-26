package com.commerceplatform.api.accounts.dtos;

public record RecoveryPasswordDto(
        String code,
        String email,
        String password
) {
}
