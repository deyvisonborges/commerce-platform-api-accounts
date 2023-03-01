package com.commerceplatform.api.accounts.utils;

import java.util.regex.Pattern;

public class Validators {
    public static boolean isValidEmail(String email) {
        final String pattern = "^(.+)@(\\S+)$";

        return Pattern.compile(pattern)
                .matcher(email)
                .matches();
    }
}
