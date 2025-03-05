package com.backend.conferencias.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String[] errors = ex.getBindingResult().getAllErrors().stream()
        .map(error -> error.getDefaultMessage())
        .toArray(String[]::new);

    return new ResponseEntity<>(Map.of("response", Map.of("errors", errors)), HttpStatus.BAD_REQUEST);
  }
}