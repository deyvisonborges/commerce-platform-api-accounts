package com.commerceplatform.api.accounts.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class Validators {
    public final Map<String, List<String>> errors = new HashMap<>();

    public void addError(String attribute, String message) {
        if(!errors.containsKey(attribute)) {
            errors.put(attribute, new ArrayList<>());
        }
        errors.get(attribute).add(message);
    }

    public void min(String attribute, String value, Integer length, String message) {
        if(value.length() < length) {
            addError(attribute, message);
        }
    }

    public void max(String attribute, String value, Integer length, String message) {
        if(value.length() > length) {
            addError(attribute, message);
        }
    }

    public void isRequired(String attribute, String value, String message) {
        if(value == null || value.length() <= 0) {
            addError(attribute, message);
        }
    }

    public boolean isValidEmail(String email) {
        final String pattern = "^(.+)@(\\S+)$";

        return Pattern.compile(pattern)
            .matcher(email)
            .matches();
    }

    public Boolean validate() {
        return errors.isEmpty();
    }

    public List<String> getErrorsByAttribute(String attribute) {
        return errors.getOrDefault(attribute, new ArrayList<>());
    }

    public Map<String, List<String>> getAllErrors() {
        return errors;
    }
}
