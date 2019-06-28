package com.codingtest.demo.apiControllerTests;


import com.codingtest.demo.controllers.PaymentsController;
import com.codingtest.demo.dtos.PaymentInformationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsController.class)
public class PaymentAPITests {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private PaymentsController paymentsController;

    //Tests for payments/validate
    @Test
    public void givenNoBindingResults_whenPostToCreateNewUser_returnOKStatusAndNewUserInformation() throws Exception {
        PaymentInformationDTO paymentInformationDTO = new PaymentInformationDTO();
        paymentInformationDTO.setCreditCardNumber(5555555555554444L);
        paymentInformationDTO.setCvv(123);
        Date date = new GregorianCalendar(2022, Calendar.FEBRUARY, 11).getTime();
        paymentInformationDTO.setExpiryDate(date);
        paymentInformationDTO.setPaymentAddress("123 Payment Address");


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/payments/validate")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(paymentInformationDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenNoPaymentInfoFields_whenPutToPaymentValidate_returnErrorStatusAndMessage() throws Exception {
        PaymentInformationDTO paymentInformationDTO2 = new PaymentInformationDTO();

        System.out.println(paymentInformationDTO2.getCreditCardNumber());
        System.out.println(paymentInformationDTO2.getCvv());
        System.out.println("wtf?");

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/payments/validate")
                .content(mapper.writeValueAsString(paymentInformationDTO2))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\"id\":13131313,\"name\":\"UserName\",\"email\":\"UserEmail@ValidEmail.com\",\"address\":\"User Address\"}"));
    }
}
