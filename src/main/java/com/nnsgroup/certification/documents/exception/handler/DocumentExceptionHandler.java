package com.nnsgroup.certification.documents.exception.handler;

import com.nnsgroup.certification.documents.exception.DocumentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DocumentExceptionHandler {

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<String> handleProviderNotFound(DocumentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
