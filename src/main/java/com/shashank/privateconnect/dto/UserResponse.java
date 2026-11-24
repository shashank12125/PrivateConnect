package com.shashank.privateconnect.dto;

public record UserResponse(
        Long id,
        String username,
        String email,
        String message
) {}