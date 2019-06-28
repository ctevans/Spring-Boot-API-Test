package com.codingtest.demo.services;

import com.codingtest.demo.dtos.UserDTO;
import com.codingtest.demo.models.User;

/**
 * Service dedicated to the handling of any User information in the system.
 */
public interface UserService {
    User createNewUser(UserDTO userDTO);
}
