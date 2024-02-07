package com.user.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.demo.dto.UserDTO;
import com.user.demo.dto.UserDTOMapper;
import com.user.demo.entities.User;
import com.user.demo.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private UserDTOMapper userDTOMapper;

  @GetMapping
  @Operation(summary = "Get all users, with pagination and sorting")
  public ResponseEntity<Page<UserDTO>> getAllUsers(
    @RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "25") int size,
    @RequestParam(required = false, defaultValue = "id") String sort
  ) {
    logger.info("Request to get all users, page: {}, size: {}, sort: {}", page, size, sort);

    Page<UserDTO> userResponseDTOs = userService
      .findAll(page, size, Sort.by(sort))
      .map(userDTOMapper);

    return ResponseEntity.ok(userResponseDTOs);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get user by ID")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    logger.info("Request to get user: {}", id);

    User user = userService.findById(id);
    UserDTO userDTO = userDTOMapper.apply(user);
    return ResponseEntity.ok(userDTO);
  }

  @PostMapping
  @Operation(summary = "Create a new user")
  public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO) {
    User user = new User(userDTO.name(), userDTO.lastname());
    logger.info("Request to create user: {}", user);
    
    User savedUser = userService.save(user);
    UserDTO savedUserDTO = userDTOMapper.apply(savedUser);
    return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing user")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Validated @RequestBody UserDTO userDTO) {
    User user = new User(id, userDTO.name(), userDTO.lastname());
    logger.info("Request to update user: {}", user);

    User updatedUser = userService.save(user);
    UserDTO updatedUserDTO = userDTOMapper.apply(updatedUser);
    return ResponseEntity.ok(updatedUserDTO);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an existing user")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    logger.info("Request to delete user: {}", id);

    userService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
