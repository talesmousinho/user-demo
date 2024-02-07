package com.user.demo.controllers;

import com.user.demo.entities.User;
import com.user.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private User user;

  @BeforeEach
  public void setUp() {
    user = new User(1L, "John", "Doe");
  }

  @Test
  public void testGetAllUsers() throws Exception {
    Page<User> userPage = new PageImpl<>(Arrays.asList(user));

    when(userService.findAll(anyInt(), anyInt(), any(Sort.class))).thenReturn(userPage);

    mockMvc.perform(get("/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content[0].id").value(user.getId()))
      .andExpect(jsonPath("$.content[0].name").value(user.getName()))
      .andExpect(jsonPath("$.content[0].lastname").value(user.getLastname()));
  }

  @Test
  public void testGetUserById() throws Exception {
    when(userService.findById(anyLong())).thenReturn(user);

    mockMvc.perform(get("/api/v1/users/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(user.getId()))
      .andExpect(jsonPath("$.name").value(user.getName()))
      .andExpect(jsonPath("$.lastname").value(user.getLastname()));
  }

  @Test
  public void testCreateUser() throws Exception {
    when(userService.save(any(User.class))).thenReturn(user);

    mockMvc.perform(
      post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(String.format("{\"name\":\"%s\", \"lastname\":\"%s\"}", user.getName(), user.getLastname()))
      )
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(user.getId()))
      .andExpect(jsonPath("$.name").value(user.getName()))
      .andExpect(jsonPath("$.lastname").value(user.getLastname()));
  }

  @Test
  public void testUpdateUser() throws Exception {
    User updatedUser = new User("Updated", "User");
    when(userService.save(any(User.class))).thenReturn(updatedUser);

    mockMvc.perform(put("/api/v1/users/1")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"name\":\"Updated User\", \"lastname\":\"User\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(updatedUser.getId()))
      .andExpect(jsonPath("$.name").value(updatedUser.getName()))
      .andExpect(jsonPath("$.lastname").value(updatedUser.getLastname()));
  }

  @Test
  public void testDeleteUser() throws Exception {
    mockMvc.perform(delete("/api/v1/users/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }
}