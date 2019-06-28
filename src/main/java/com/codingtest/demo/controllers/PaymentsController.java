package com.codingtest.demo.controllers;

import com.codingtest.demo.dtos.PaymentInformationDTO;
import com.codingtest.demo.services.PaymentService;
import com.codingtest.demo.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Exposed API Endpoint for the system to handle any payment-related actions.
 */
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentService paymentService;
    private final APIUtils apiUtils;

    @Autowired
    public PaymentsController(PaymentService paymentService, APIUtils apiUtils) {
        this.paymentService = paymentService;
        this.apiUtils = apiUtils;
    }

    @PostMapping(path = "/validate")
    public ResponseEntity validatePaymentInformation(@Valid @RequestBody PaymentInformationDTO paymentInformationDTO, BindingResult bindingResult) {
        ResponseEntity responseEntityFromBindingResult = apiUtils.handleBindingErrorsAndReturnThemFormattedNicely(bindingResult);
        if (responseEntityFromBindingResult != null) {
            return responseEntityFromBindingResult;
        }

        //If there were no validation errors above, then the payment information has a complete payload.
        return ResponseEntity.status(HttpStatus.OK).body("Valid Payment Information Provided");
    }
}
