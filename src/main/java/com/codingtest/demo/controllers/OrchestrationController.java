package com.codingtest.demo.controllers;

import com.codingtest.demo.dtos.OrchestrationDTO;
import com.codingtest.demo.models.User;
import com.codingtest.demo.services.UserService;
import com.codingtest.demo.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Exposed API Endpoint for the system that will orchestrate the calls between the user creation,
 * and the payment processing APIs.
 */
@RestController
@RequestMapping("/orchestration")
public class OrchestrationController {

    private final APIUtils apiUtils;
    private final UserService userService;

    @Autowired
    public OrchestrationController(APIUtils apiUtils, UserService userService) {
        this.apiUtils = apiUtils;
        this.userService = userService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity createNewUser(@Valid @RequestBody OrchestrationDTO orchestrationDTO, BindingResult bindingResult) {
        User user = null;
        ResponseEntity responseEntityFromBindingResult = apiUtils.handleBindingErrorsAndReturnThemFormattedNicely(bindingResult);
        if (responseEntityFromBindingResult != null) {
            return responseEntityFromBindingResult;
        }

        try {
            //First create a new user.
            user = userService.createNewUser(orchestrationDTO.getUserDTO());
            //Then validation of complete payment information being provided was done prior to this step by annotations.
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Valid Payment Information Provided, New User ID: " + (user != null ? user.getId() : null));
    }
}
