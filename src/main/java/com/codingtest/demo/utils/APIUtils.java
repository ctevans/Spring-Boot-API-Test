package com.codingtest.demo.utils;

import com.codingtest.demo.utils.Errors.ApiValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods to assist the API Controllers.
 */
@Component
public class APIUtils {
    /**
     * Parses the binding results provided from the API Controllers and will return any packaged nicely
     * for API Endpoint invocators to read. Otherwise this will return null.
     *
     * @param bindingResult Binding Result produced from validation of the incoming request body.
     * @return Returns null if no binding errors occurred. Otherwise returns a response entity.
     */
    public ResponseEntity handleBindingErrorsAndReturnThemFormattedNicely(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            ArrayList<ApiValidationError> apiValidationErrors = new ArrayList<>();
            for (FieldError fieldError : errors) {
                ApiValidationError apiValidationError = new ApiValidationError(fieldError.getObjectName(), fieldError.getField(),
                        fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "",
                        fieldError.getDefaultMessage());
                apiValidationErrors.add(apiValidationError);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiValidationErrors);
        }

        return null;
    }
}
