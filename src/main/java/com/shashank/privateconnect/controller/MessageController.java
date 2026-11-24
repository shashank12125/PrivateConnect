
package com.shashank.privateconnect.controller;

import com.shashank.privateconnect.dto.MessageRequest;
import com.shashank.privateconnect.dto.MessageResponse;
import com.shashank.privateconnect.entity.Message;
import com.shashank.privateconnect.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public MessageResponse sendMessage(
            @RequestBody MessageRequest request,
            Authentication authentication) {

        Message message = messageService.sendMessage(
                authentication.getName(),
                request.receiverEmail(),
                request.content()
        );

        return toResponse(message);
    }

    @GetMapping("/conversation/{email}")
    public List<MessageResponse> getConversation(
            @PathVariable String email,
            Authentication authentication) {

        return messageService
                .getConversation(authentication.getName(), email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getSender().getEmail(),
                message.getReceiver().getEmail(),
                message.getContent(),
                message.getSentAt(),
                message.getStatus()
        );
    }

    @PutMapping("/{messageId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long messageId,
            @RequestParam String status) {

        messageService.updateStatus(messageId, status);
        return ResponseEntity.ok("Status updated: " + status);
    }

}
