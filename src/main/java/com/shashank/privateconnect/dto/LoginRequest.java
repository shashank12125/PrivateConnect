package com.shashank.privateconnect.dto;

public record LoginRequest(
        String email,
        String password
) {}