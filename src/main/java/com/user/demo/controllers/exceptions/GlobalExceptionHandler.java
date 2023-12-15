package com.user.demo.controllers.exceptions;


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
import com.user.demo.controllers.responses.CustomErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
    CustomErrorResponse errors = new CustomErrorResponse();
    errors.setTimestamp(LocalDateTime.now());
    errors.setStatus(HttpStatus.BAD_REQUEST.value());
    errors.setError("Bad Request");
    errors.setPath(request.getRequestURI());

    Map<String, String> errorMessages = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errorMessages.put(fieldName, errorMessage);
    });
    errors.setMessages(errorMessages);

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
    CustomErrorResponse errors = new CustomErrorResponse();
    errors.setTimestamp(LocalDateTime.now());
    errors.setStatus(HttpStatus.BAD_REQUEST.value());
    errors.setError("Bad Request");
    errors.setPath(request.getRequestURI());

    Throwable rootCause = ex.getMostSpecificCause();
    if (rootCause instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
      String errorMessage = "Invalid format: " + invalidFormatException.getValue();
      errors.setMessages(Collections.singletonMap("error", errorMessage));
    } else {
      // Generic error message for other types of HttpMessageNotReadableException
      errors.setMessages(Collections.singletonMap("error", "Malformed JSON request"));
    }

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
}
