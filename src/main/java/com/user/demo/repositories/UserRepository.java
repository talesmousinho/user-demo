package com.user.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.demo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
