package com.commerceplatform.api.accounts.utils;

import java.util.List;
import java.util.regex.Pattern;

public class Validators {

    private final List<String> errors;

    private Validators() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }


    private void addError(String message) {
        errors.add(message);
    }

    protected void hasMinLen(String value, Integer length, String message) {
        if(value.length() < length) {
            addError(message);
        }
    }

    public static boolean isValidEmail(String email) {
        final String pattern = "^(.+)@(\\S+)$";

        return Pattern.compile(pattern)
            .matcher(email)
            .matches();
    }

    protected Boolean validate() {
        return errors.isEmpty();
    }


    protected List<String> getErrors() {
        return this.errors;
    }
}
