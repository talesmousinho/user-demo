package com.user.demo.controllers.responses;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResponse {

  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String path;
  private Map<String, String> messages;

}
