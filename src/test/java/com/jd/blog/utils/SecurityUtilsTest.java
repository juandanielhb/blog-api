package com.jd.blog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SecurityUtilsTest {

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Test
    public void testEncrypt() {
        String data = "password123";

        String encryptedData = SecurityUtils.encode(data);

        assertTrue(new BCryptPasswordEncoder().matches(data, encryptedData));
    }

    @Test
    public void testGetCurrentUsername() {
        String expectedUsername = "testuser";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(expectedUsername);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String username = SecurityUtils.getCurrentUsername();

        assertEquals(expectedUsername, username);
    }

    @Test
    public void testGetCurrentUsernameWhenNotAuthenticated() {
        SecurityContextHolder.clearContext();
        String username = SecurityUtils.getCurrentUsername();
        assertNull(username);
    }

    @Test
    public void testGetCurrentUserDetails() {
        String expectedUsername = "testuser";
        UserDetails userDetails = new User(expectedUsername, "password", null);
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn(expectedUsername);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDetails currentUserDetails = SecurityUtils.getCurrentUserDetails();

        assertEquals(userDetails, currentUserDetails);
    }

    @Test
    public void testGetCurrentUserDetailsWhenNotAuthenticated() {
        SecurityContextHolder.clearContext();
        UserDetails currentUserDetails = SecurityUtils.getCurrentUserDetails();
        assertNull(currentUserDetails);
    }
}
