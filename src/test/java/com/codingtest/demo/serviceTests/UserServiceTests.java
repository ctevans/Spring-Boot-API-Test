package com.codingtest.demo.serviceTests;

import com.codingtest.demo.controllers.UserController;
import com.codingtest.demo.dtos.UserDTO;
import com.codingtest.demo.models.User;
import com.codingtest.demo.repositories.UserRepository;
import com.codingtest.demo.services.UserService;

import static org.junit.jupiter.api.Assertions.*;

import com.codingtest.demo.services.UserServiceImpl;
import com.codingtest.demo.utils.APIUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    APIUtils apiUtils;
    private User mockValidUser = new User();

    @Before
    public void setup() {
        //MockValidUser Setup.
        mockValidUser.setId(13131313);
        mockValidUser.setAddress("User Address");
        mockValidUser.setEmail("UserEmail@ValidEmail.com");
        mockValidUser.setName("UserName");
    }

    //Tests for the createNewUser method.
    @Test
    public void givenValidUserInfo_whenCreatingNewUserInUserService_returnsANewUserFromTheMethod() throws Exception {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setAddress("User Address");
        mockUserDTO.setEmail("UserEmail@ValidEmail.com");
        mockUserDTO.setName("UserName");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(mockValidUser);
        User userFromMethodInvocation = userService.createNewUser(mockUserDTO);
        assertEquals(mockUserDTO.getAddress(), "User Address");
        assertEquals(mockUserDTO.getEmail(), "UserEmail@ValidEmail.com");
        assertEquals(mockUserDTO.getName(), "UserName");
    }
}