package com.codingtest.demo.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Data Transfer Object for receiving the incoming User objects through the UserController.
 */
public class PaymentInformationDTO {
    @NotNull(message = "Credit Card Number is mandatory.")
    private Long creditCardNumber;
    @NotNull(message = "Expiry Date is mandatory.")
    private Date expiryDate;
    @NotNull(message = "CVV is mandatory.")
    private Integer cvv;
    @NotBlank(message = "Payment Address is mandatory.")
    @Size(max = 24, message = "Payment Address Value Provided is Too Long.")
    private String paymentAddress;

    //<editor-fold desc="Getters/Setters">
    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }
    //</editor-fold>
}
