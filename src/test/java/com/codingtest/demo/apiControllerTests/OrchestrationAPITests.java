package com.codingtest.demo.apiControllerTests;

import com.codingtest.demo.controllers.OrchestrationController;
import com.codingtest.demo.controllers.PaymentsController;
import com.codingtest.demo.dtos.OrchestrationDTO;
import com.codingtest.demo.dtos.PaymentInformationDTO;
import com.codingtest.demo.dtos.UserDTO;
import com.codingtest.demo.models.User;
import com.codingtest.demo.services.UserServiceImpl;
import com.codingtest.demo.utils.APIUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@WebMvcTest(OrchestrationController.class)
public class OrchestrationAPITests {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private User mockValidUser = new User();
    @MockBean
    PaymentsController paymentsController;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private APIUtils apiUtils;

    @Before
    public void setup() {
        //MockValidUser Setup.
        mockValidUser.setId(13131313);
        mockValidUser.setAddress("User Address");
        mockValidUser.setEmail("UserEmail@ValidEmail.com");
        mockValidUser.setName("UserName");
    }

    //Tests for orchestration/create
    @Test
    public void givenFullOrchestrationInfoNoIssues_whenPostToCreateNewOrchestration_returnOKStatusAndValidInformation() throws Exception {
        PaymentInformationDTO paymentInformationDTO = new PaymentInformationDTO();
        paymentInformationDTO.setCreditCardNumber(5555555555554444L);
        paymentInformationDTO.setCvv(123);
        Date date = new GregorianCalendar(2022, Calendar.FEBRUARY, 11).getTime();
        paymentInformationDTO.setExpiryDate(date);
        paymentInformationDTO.setPaymentAddress("123 Payment Address");

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setAddress("User Address");
        mockUserDTO.setEmail("UserEmail@ValidEmail.com");
        mockUserDTO.setName("UserName");

        OrchestrationDTO orchestrationDTO = new OrchestrationDTO();
        orchestrationDTO.setPaymentInformationDTO(paymentInformationDTO);
        orchestrationDTO.setUserDTO(mockUserDTO);

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orchestration/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(orchestrationDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "Valid Payment Information Provided, New User ID: 13131313"));
    }

    @Test
    public void givenOrchestrationInfoNoUserDTO_whenPostToCreateNewOrchestration_returnOKStatusAndValidInformation() throws Exception {
        PaymentInformationDTO paymentInformationDTO = new PaymentInformationDTO();
        paymentInformationDTO.setCreditCardNumber(5555555555554444L);
        paymentInformationDTO.setCvv(123);
        Date date = new GregorianCalendar(2022, Calendar.FEBRUARY, 11).getTime();
        paymentInformationDTO.setExpiryDate(date);
        paymentInformationDTO.setPaymentAddress("123 Payment Address");

        OrchestrationDTO orchestrationDTO = new OrchestrationDTO();
        orchestrationDTO.setPaymentInformationDTO(paymentInformationDTO);

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orchestration/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(orchestrationDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[\n" +
                                "    {\n" +
                                "        \"object\": \"orchestrationDTO\",\n" +
                                "        \"field\": \"userDTO\",\n" +
                                "        \"rejectedValue\": \"\",\n" +
                                "        \"message\": \"must not be null\"\n" +
                                "    }\n" +
                                "]"));
    }

    @Test
    public void givenOrchestrationInfoNoPaymentDTO_whenPostToCreateNewOrchestration_returnOKStatusAndValidInformation() throws Exception {
        OrchestrationDTO orchestrationDTO = new OrchestrationDTO();

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setAddress("User Address");
        mockUserDTO.setEmail("UserEmail@ValidEmail.com");
        mockUserDTO.setName("UserName");

        orchestrationDTO.setUserDTO(mockUserDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orchestration/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(orchestrationDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[[\n" +
                                "    {\n" +
                                "        \"object\": \"orchestrationDTO\",\n" +
                                "        \"field\": \"paymentInformationDTO\",\n" +
                                "        \"rejectedValue\": \"\",\n" +
                                "        \"message\": \"must not be null\"\n" +
                                "    }\n" +
                                "]"));
    }
}
