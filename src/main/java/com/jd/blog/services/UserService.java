package com.jd.blog.services;

import com.jd.blog.exceptions.ResourceCreationException;
import com.jd.blog.dtos.AuthRequest;
import com.jd.blog.dtos.TokenInfo;
import com.jd.blog.exceptions.ResourceNotFoundException;
import com.jd.blog.exceptions.UserAlreadyExistsException;
import com.jd.blog.model.User;
import com.jd.blog.repository.UserRepository;
import com.jd.blog.utils.SecurityUtils;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public List<User> getUsers() {
        log.info("Fetching all users.");
        return userRepository.findAll();
    }
    public TokenInfo create(User user) {
        try {
            log.info("Creating a new user: {} {}", user.getName(), user.getSurname());
            String rawPassword = user.getPassword();
            user.setPassword(SecurityUtils.encode(rawPassword));
            user.setUsername(user.getEmail());

            if (userRepository.findOneByEmail(user.getEmail()).isPresent()) {
                log.error("Email {} is already in use.", user.getEmail());
                throw new UserAlreadyExistsException("Failed to create the user. Please check the data and try again.");
            }

            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            User savedUser = userRepository.save(user);

            authenticateUser(savedUser.getUsername(), rawPassword);
            final String jwt = jwtService.generateToken(user);

            return new TokenInfo(jwt);
        } catch (MongoException ex) {
            log.error("Failed to create the user. Please check the data and try again.", ex);
            throw new ResourceCreationException("Failed to create the user. Please check the data and try again.", ex);
        }
    }

    public TokenInfo login(AuthRequest authRequest) {
        log.info("Authenticating the user {}", authRequest.getEmail());

        authenticateUser(authRequest.getEmail(), authRequest.getPassword());

        User user = findUserByUsername(authRequest.getEmail());

        final String jwt = jwtService.generateToken(user);

        return new TokenInfo(jwt);
    }

    User findUserByUsername(String username) {
        log.info("Finding user by username: {}", username);
        return userRepository.findOneByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                    String.format("Failed to retrieve the user by the specified username %s.", username)
                ));
    }

     User loggedUserInfo(){
        String username = SecurityUtils.getCurrentUsername();
        return findUserByUsername(username);
    }

    void authenticateUser(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }
}