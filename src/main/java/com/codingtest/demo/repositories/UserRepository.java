package com.codingtest.demo.repositories;

import com.codingtest.demo.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}