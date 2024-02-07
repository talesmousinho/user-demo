package com.user.demo.dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.user.demo.entities.User;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getLastname());
    }
}
