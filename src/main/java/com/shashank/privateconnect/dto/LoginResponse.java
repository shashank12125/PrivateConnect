package com.shashank.privateconnect.dto;

public record LoginResponse(
        Long id,
        String username,
        String email,
        String token,
        String message
) {}