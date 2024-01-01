package com.user.demo.utils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);

    Map<String, String> errorMessages = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errorMessages.put(fieldName, errorMessage);
    });

    return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
    Map<String, String> errorMessages = new HashMap<>();
    errorMessages.put("timestamp", LocalDateTime.now().toString());
    errorMessages.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
    errorMessages.put("error", "Bad Request");
    errorMessages.put("path", request.getRequestURI());

    Throwable rootCause = ex.getMostSpecificCause();
    if (rootCause instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
      String errorMessage = "Invalid format: " + invalidFormatException.getValue();
      errorMessages.put("message", errorMessage);
    } else {
      // Generic error message for other types of HttpMessageNotReadableException
      errorMessages.put("message", "Malformed JSON request");
    }

    return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
    Map<String, String> errorMessages = Collections.singletonMap("error", ex.getMessage());
    return new ResponseEntity<>(errorMessages, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleException(Exception ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
    Map<String, String> errorMessages = Collections.singletonMap("error", "Internal Server Error");
    return new ResponseEntity<>(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}