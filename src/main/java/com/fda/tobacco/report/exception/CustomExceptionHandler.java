package com.fda.tobacco.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(ProductNotFoundException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(HttpClientErrorException.NotFound exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, Object>> handleHttpClientError(HttpClientErrorException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", exception.getStatusCode().value());
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, exception.getStatusCode());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, Object>> handleMissingParamException(Exception exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "An unexpected error occurred.");
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
