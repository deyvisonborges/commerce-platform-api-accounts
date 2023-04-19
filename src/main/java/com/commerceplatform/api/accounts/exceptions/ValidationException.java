package com.commerceplatform.api.accounts.exceptions;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException{
    public ValidationException(Map<String, List<String>> errors) {
        super("" + errors);
    }
}
