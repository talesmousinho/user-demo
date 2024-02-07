package com.user.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.user.demo.entities.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
