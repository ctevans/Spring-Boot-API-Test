package com.codingtest.demo.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class OrchestrationDTO {
    @NotNull
    @Valid
    private PaymentInformationDTO paymentInformationDTO;
    @NotNull
    @Valid
    private UserDTO userDTO;

    //<editor-fold desc="Getters/Setters">
    public PaymentInformationDTO getPaymentInformationDTO() {
        return paymentInformationDTO;
    }

    public void setPaymentInformationDTO(PaymentInformationDTO paymentInformationDTO) {
        this.paymentInformationDTO = paymentInformationDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
    //</editor-fold>
}
