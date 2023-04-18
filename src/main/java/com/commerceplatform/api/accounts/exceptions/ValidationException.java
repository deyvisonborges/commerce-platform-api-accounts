package com.commerceplatform.api.accounts.exceptions;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException{
//    private final Map<String, List<String>> errors;
    public ValidationException(Map<String, List<String>> errors) {
        super("Validation errors: " + errors);
//        this.errors = errors;
    }

//    public Map<String, List<String>> getErrors() {
//        return errors;
//    }
}
