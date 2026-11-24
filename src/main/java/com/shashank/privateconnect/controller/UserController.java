package com.shashank.privateconnect.controller;

import com.shashank.privateconnect.dto.UserResponse;
import com.shashank.privateconnect.entity.User;
import com.shashank.privateconnect.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.shashank.privateconnect.dto.LoginRequest;
import com.shashank.privateconnect.dto.UserResponse;
import com.shashank.privateconnect.service.JwtService;
import com.shashank.privateconnect.dto.LoginResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public UserResponse register(@RequestBody User user) {

        User savedUser = userService.registerUser(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                "Registration successful"
        );
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userService.loginUser(
                request.email(),
                request.password()
        );

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                token,
                "Login successful"
        );
    }

    @GetMapping("/profile")
    public String getProfile(
            org.springframework.security.core.Authentication authentication) {

        return "Welcome! Logged in as: " + authentication.getName();
    }

}