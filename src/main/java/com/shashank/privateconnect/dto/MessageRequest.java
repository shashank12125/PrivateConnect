package com.shashank.privateconnect.dto;

public record MessageRequest(
        String receiverEmail,
        String content
) {}