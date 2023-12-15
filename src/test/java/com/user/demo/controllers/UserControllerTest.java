package com.user.demo.controllers;

import com.user.demo.entities.User;
import com.user.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    user = new User();
    user.setId(1L);
    user.setName("Test");
    user.setSurname("User");
    user.setBirthdate(new Date());
  }

  @Test
  public void testGetAllUsers() throws Exception {
    when(userService.findAll()).thenReturn(Arrays.asList(user));

    mockMvc.perform(get("/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  public void testGetUserById() throws Exception {
    when(userService.findById(anyLong())).thenReturn(user);

    mockMvc.perform(get("/api/v1/users/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  public void testCreateUser() throws Exception {
    when(userService.save(any(User.class))).thenReturn(user);

    mockMvc.perform(post("/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"name\":\"Test User\", \"surname\":\"User\", \"birthdate\":\"1990-01-01\"}"))
      .andExpect(status().isCreated());
  }

  @Test
  public void testUpdateUser() throws Exception {
    when(userService.save(any(User.class))).thenReturn(user);

    mockMvc.perform(put("/api/v1/users/1")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"name\":\"Updated User\", \"surname\":\"User\", \"birthdate\":\"1990-01-01\"}"))
      .andExpect(status().isOk());
  }

  @Test
  public void testDeleteUser() throws Exception {
    mockMvc.perform(delete("/api/v1/users/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }
}