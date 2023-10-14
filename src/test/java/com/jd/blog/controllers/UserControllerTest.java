package com.jd.blog.controllers;

import com.jd.blog.controllers.UserController;
import com.jd.blog.dtos.AuthRequest;
import com.jd.blog.dtos.TokenInfo;
import com.jd.blog.model.User;
import com.jd.blog.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignup() {
        // Create a sample User for testing
        User user = new User();
        user.setEmail("testuser@user.com");
        user.setPassword("testpassword");

        // Mock the behavior of the userService
        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(user);

        // Call the controller method
        User response = userController.signup(user);

        // Verify the response and status code
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(HttpStatus.CREATED.value(), HttpStatus.CREATED.value());
    }

    @Test
    public void testLogin() {
        // Create a sample AuthRequest for testing
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("testuser@user.com");
        authRequest.setPassword("testpassword");

        // Mock the behavior of the userService
        TokenInfo tokenInfo = new TokenInfo("yourToken"); // Replace with a valid token
        Mockito.when(userService.login(Mockito.any(AuthRequest.class))).thenReturn(tokenInfo);

        // Call the controller method
        TokenInfo response = userController.login(authRequest);

        // Verify the response and status code
        assertEquals(tokenInfo.jwt(), response.jwt());
        assertEquals(HttpStatus.OK.value(), HttpStatus.OK.value());
    }
}