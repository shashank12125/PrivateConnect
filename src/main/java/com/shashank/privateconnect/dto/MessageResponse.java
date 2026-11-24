package com.shashank.privateconnect.dto;

import java.time.LocalDateTime;

public record MessageResponse(
        Long id,
        String senderEmail,
        String receiverEmail,
        String content,
        LocalDateTime sentAt,
        String status
) {}