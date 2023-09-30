package com.url.shortener.exception;

import static com.url.shortener.exception.ExceptionRule.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.url.shortener.dto.response.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 NOT FOUND
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        String requestUrl = e.getRequestURL();
        String message = NOT_FOUND.getMessage();

        log.error("[ERROR] {} => 요청 URL : {}", message, requestUrl, e);

        ExceptionResponse response = ExceptionResponse.builder()
            .requestUrl(requestUrl)
            .message(message)
            .build();

        return ResponseEntity.status(NOT_FOUND.getStatus()).body(response);
    }

    // 405 METHOD NOT ALLOWED
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e,
        HttpServletRequest request
    ) {
        String message = METHOD_NOT_ALLOWED.getMessage();

        log.error("[ERROR] {} => 지원 메서드 : {} | 요청 메서드 : {}", message, e.getSupportedMethods(), e.getMethod(), e);

        ExceptionResponse response = ExceptionResponse.builder()
            .requestUrl(request.getRequestURI())
            .message(message)
            .build();

        return ResponseEntity.status(METHOD_NOT_ALLOWED.getStatus()).body(response);
    }

    // Request DTO Field Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e,
        HttpServletRequest request
    ) {
        String message = BAD_REQUEST.getMessage();
        List<FieldError> errors = e.getBindingResult()
            .getAllErrors()
            .stream()
            .map(FieldError.class::cast)
            .toList();

        log.error("[ERROR] {}", message, e);
        for (FieldError error : errors) {
            log.error("{} => {} : {}", error.getDefaultMessage(), error.getField(), error.getRejectedValue());
        }

        Object[] rejectedValues = errors.stream()
            .map(FieldError::getRejectedValue)
            .toArray();

        ExceptionResponse response = ExceptionResponse.builder()
            .requestUrl(request.getRequestURI())
            .message(message)
            .rejectedValues(rejectedValues)
            .build();

        return ResponseEntity.status(BAD_REQUEST.getStatus()).body(response);
    }

    // Business Exception Handler
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException e, HttpServletRequest request) {
        ExceptionRule exceptionRule = e.getExceptionRule();
        Object[] rejectedValues = e.getRejectedValues();
        String message = exceptionRule.getMessage();

        log.error("[ERROR] {} => 원인 값 : {}", message, rejectedValues, e);

        ExceptionResponse response = ExceptionResponse.builder()
            .requestUrl(request.getRequestURI())
            .message(message)
            .rejectedValues(rejectedValues)
            .build();

        return ResponseEntity.status(exceptionRule.getStatus()).body(response);
    }

    // Unexpected Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e, HttpServletRequest request) {
        String message = INTERNAL_SERVER_ERROR.getMessage();

        log.error("[ERROR] {}", message, e);

        ExceptionResponse response = ExceptionResponse.builder()
            .requestUrl(request.getRequestURI())
            .message(message)
            .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR.getStatus()).body(response);
    }
}
