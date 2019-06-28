package com.codingtest.demo.apiControllerTests;

import com.codingtest.demo.controllers.UserController;
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

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserAPITests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    private ObjectMapper mapper = new ObjectMapper();
    private User mockValidUser = new User();
    @MockBean
    APIUtils apiUtils;

    @Before
    public void setup() {
        //MockValidUser Setup.
        mockValidUser.setId(13131313);
        mockValidUser.setAddress("User Address");
        mockValidUser.setEmail("UserEmail@ValidEmail.com");
        mockValidUser.setName("UserName");
    }

    //Tests for users/create
    @Test
    public void givenValidUserInfo_whenPostToCreateNewUser_returnOKStatusAndNewUserInformation() throws Exception {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setAddress("User Address");
        mockUserDTO.setEmail("UserEmail@ValidEmail.com");
        mockUserDTO.setName("UserName");

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string(
                        "New User Created with ID: 13131313"));
    }

    @Test
    public void givenInvalidUserInfoMissingEmail_whenPostToCreateNewUser_returnErrorStatusAndMessage() throws Exception {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setAddress("User Address");
        mockUserDTO.setName("UserName");

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"object\":\"UserDTO\"," +
                                "\"field\":\"email\"," +
                                "\"rejectedValue\":\"\"," +
                                "\"message\":\"Email is mandatory\"}]"));
    }

    @Test
    public void givenInvalidUserInfoInvalidEmail_whenPostToCreateNewUser_returnErrorStatusAndMessage() throws Exception {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setAddress("User Address");
        mockUserDTO.setName("UserName");
        mockUserDTO.setEmail("invalidNotAnEmail");

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(
                        "[\n" +
                                "    {\n" +
                                "        \"object\": \"userDTO\",\n" +
                                "        \"field\": \"email\",\n" +
                                "        \"rejectedValue\": \"invalidNotAnEmail\",\n" +
                                "        \"message\": \"Invalid email address provided.\"\n" +
                                "    }\n" +
                                "]"));
    }

    @Test
    public void givenInvalidUserInfoMissingAddress_whenPostToCreateNewUser_returnErrorStatusAndMessage() throws Exception {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setEmail("UserEmail@ValidEmail.com");
        mockUserDTO.setName("UserName");

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"object\":\"UserDTO\"," +
                                "\"field\":\"address\"," +
                                "\"rejectedValue\":\"\"," +
                                "\"message\":\"Address is mandatory\"}]"));
    }

    @Test
    public void givenInvalidUserInfoMissingUsername_whenPostToCreateNewUser_returnErrorStatusAndMessage() throws Exception {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setAddress("User Address");
        mockUserDTO.setEmail("UserEmail@ValidEmail.com");

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"object\":\"UserDTO\"," +
                                "\"field\":\"name\"," +
                                "\"rejectedValue\":\"\"," +
                                "\"message\":\"Name is mandatory\"}]"));
    }

    @Test
    public void givenInvalidUserInfoMissingUsernameAndEmail_whenPostToCreateNewUser_returnErrorStatusAndMessage() throws Exception {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setAddress("User Address");

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"object\":\"UserDTO\"," +
                                "\"field\":\"email\"," +
                                "\"rejectedValue\":\"\"," +
                                "\"message\":\"Email is mandatory\"}," +
                                "{\"object\":\"UserDTO\"," +
                                "\"field\":\"name\"," +
                                "\"rejectedValue\":\"\"," +
                                "\"message\":\"Name is mandatory\"}]"));
    }

    @Test
    public void givenInvalidUserInfoMissingAllThreeFields_whenPostToCreateNewUser_returnErrorStatusAndMessage() throws Exception {
        UserDTO mockUserDTO = new UserDTO();

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(mockValidUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/create")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserDTO))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"object\":\"UserDTO\"," +
                                "\"field\":\"email\"," +
                                "\"rejectedValue\":\"\"," +
                                "\"message\":\"Email is mandatory\"}," +
                                "{\"object\":\"UserDTO\"," +
                                "\"field\":\"name\"," +
                                "\"rejectedValue\":\"\"," +
                                "\"message\":\"Name is mandatory\"}," +
                                "{\"object\":\"UserDTO\"," +
                                "\"field\":\"address\"," +
                                "\"rejectedValue\":\"\"," +
                                "\"message\":\"Address is mandatory\"}]"));
    }
}
