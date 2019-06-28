package com.codingtest.demo.controllers;

import com.codingtest.demo.dtos.UserDTO;
import com.codingtest.demo.services.UserService;
import com.codingtest.demo.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Exposed API Endpoint for the system to handle any user-related actions.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final APIUtils apiUtils;

    @Autowired
    public UserController(UserService userService, APIUtils apiUtils) {
        this.userService = userService;
        this.apiUtils = apiUtils;
    }

    @PutMapping(path = "/create")
    public ResponseEntity createNewUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        ResponseEntity responseEntityFromBindingResult = apiUtils.handleBindingErrorsAndReturnThemFormattedNicely(bindingResult);
        if (responseEntityFromBindingResult != null) {
            return responseEntityFromBindingResult;
        }

        try {
            return ResponseEntity.status(200).body(userService.createNewUser(userDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
        }
    }
}
