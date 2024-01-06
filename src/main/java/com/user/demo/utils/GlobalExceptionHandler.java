package com.user.demo.utils;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);

    return ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
    logger.error("Exception: ", ex);

    Throwable rootCause = ex.getMostSpecificCause();
    if (rootCause instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
      return invalidFormatException.getPath().stream()
          .collect(Collectors.toMap(JsonMappingException.Reference::getFieldName, reference -> "Invalid format"));
    } else {
      return Collections.singletonMap("error", "Malformed JSON request");
    }
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