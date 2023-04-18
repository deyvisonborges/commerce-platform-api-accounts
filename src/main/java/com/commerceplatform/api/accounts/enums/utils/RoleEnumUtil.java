package com.commerceplatform.api.accounts.enums.utils;

import com.commerceplatform.api.accounts.enums.RoleEnum;

public class RoleEnumUtil {
    public static RoleEnum getName(String name) {
        if(name == null  || name.isEmpty()) {
            return null;
        }
        return RoleEnum.valueOf(name.toUpperCase());
    }
}
