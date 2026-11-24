package com.shashank.privateconnect.dto;

public record ChatMessageRequest(
        String receiverEmail,
        String content
) {}