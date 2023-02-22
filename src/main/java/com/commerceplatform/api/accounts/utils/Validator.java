package com.commerceplatform.api.accounts.utils;

import java.util.List;

public class Validator {
    private List<String> errors;

    public List<String> getErrors() {
        return this.errors;
    }

    public boolean sizeFromTo(String value, Integer min, Integer max, String message) {
        if(value.length() < min) {
            this.errors.add("The value not have be less from min");
        }
        return false;
    }
}
