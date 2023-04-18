package com.commerceplatform.api.accounts.enums.converters;

import com.commerceplatform.api.accounts.enums.RoleEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleEnumConverter implements AttributeConverter<RoleEnum, String> {
    @Override
    public String convertToDatabaseColumn(RoleEnum role) {
        if(role == null) {
            return null;
        }

        return role.getName();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String role) {
        if (role == null) {
            return null;
        }

        return Stream.of(RoleEnum.values())
            .filter(c -> c.getName().equals(role))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
