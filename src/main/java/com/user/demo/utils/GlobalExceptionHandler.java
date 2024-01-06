package com.user.demo.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);

    Map<String, String> errorMessages = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> errorMessages.put(((FieldError) error).getField(), error.getDefaultMessage()));

    return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
    Map<String, String> errorMessages = new HashMap<>();

    Throwable rootCause = ex.getMostSpecificCause();
    if (rootCause instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
      invalidFormatException.getPath().forEach(reference -> errorMessages.put(reference.getFieldName(), "Invalid format"));
    } else {
      // Generic error message for other types of HttpMessageNotReadableException
      errorMessages.put("error", "Malformed JSON request");
    }

    return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
  }

  @Order(Ordered.LOWEST_PRECEDENCE)
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public void handleException(Exception ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
  }
}