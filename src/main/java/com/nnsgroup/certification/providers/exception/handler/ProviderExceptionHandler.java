package com.nnsgroup.certification.providers.exception.handler;

import com.nnsgroup.certification.providers.exception.ProviderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProviderExceptionHandler {

    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<String> handleProviderNotFound(ProviderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
