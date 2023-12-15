package com.user.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.demo.entities.User;
import com.user.demo.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @GetMapping
  @Operation(summary = "Get all users")
  public ResponseEntity<List<User>> getAllUsers() {
    logger.info("Request to get all users");
    List<User> users = userService.findAll();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get user by ID")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    logger.info("Request to get user: {}", id);
    User user = userService.findById(id);
    return ResponseEntity.ok(user);
  }

  @PostMapping
  @Operation(summary = "Create a new user")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    user.setId(null);
    logger.info("Request to create user: {}", user);
    User savedUser = userService.save(user);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing user")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
    user.setId(id);
    logger.info("Request to update user: {}", user);
    User updatedUser = userService.save(user);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an existing user")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    logger.info("Request to delete user: {}", id);
    userService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
