package com.example.gateway.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handle(Exception ex) {
        return "API Gateway Error: " + ex.getMessage();
    }
}