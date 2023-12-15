package com.user.demo.services;

import org.springframework.stereotype.Service;

import com.user.demo.entities.User;
import com.user.demo.repositories.UserRepository;

@Service
public class UserService extends GenericCRUDService<User, Long> {

  public UserService(UserRepository repository) {
    super(repository);
  }

}
