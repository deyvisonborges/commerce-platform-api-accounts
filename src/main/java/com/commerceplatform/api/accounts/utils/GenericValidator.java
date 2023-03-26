package com.commerceplatform.api.accounts.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GenericValidator<T extends Map<String, String>> {
    private T data;
    private Map<String, String> errors;

    public GenericValidator(T initialValue) {
        this.data = initialValue;
        this.errors = new HashMap<>();
    }

    public void handleChange(String key, Function<String, String> sanitizeFn, Function<String, Boolean>[] validations, String value) {
        for (Function<String, Boolean> validation : validations) {
            if (!validation.apply(value)) {
                errors.put(key, "Error message");
                return;
            }
        }

        errors.put(key, "");

        String sanitizedValue = sanitizeFn.apply(value);
        data.put(key, sanitizedValue);
    }

    public T getData() {
        return data;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}


