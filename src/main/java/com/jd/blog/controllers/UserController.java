package com.jd.blog.controllers;

import com.jd.blog.dtos.AuthRequest;
import com.jd.blog.dtos.TokenInfo;
import com.jd.blog.model.Post;
import com.jd.blog.model.User;
import com.jd.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<User> getUsers() {
        log.info("GET /api/users - Retrieving all users");
        return userService.getUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public TokenInfo signup(@RequestBody @Valid User user) {
        log.info("POST /api/users/signup - Creating a new user");
        return userService.create(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public TokenInfo login(@RequestBody @Valid AuthRequest user) {
        log.info("POST /api/users/login - Logging in user");
        return userService.login(user);
    }
}