package com.jd.blog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testGetCurrentUsernameWhenNotAuthenticated() {
        SecurityContextHolder.clearContext();
        String username = SecurityUtils.getCurrentUsername();
        assertNull(username);
    }
}
