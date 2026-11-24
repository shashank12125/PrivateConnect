
package com.shashank.privateconnect.controller;

import com.shashank.privateconnect.dto.ChatMessageRequest;
import com.shashank.privateconnect.dto.MessageStatusRequest;
import com.shashank.privateconnect.dto.MessageResponse;
import com.shashank.privateconnect.entity.Message;
import com.shashank.privateconnect.service.MessageService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import com.shashank.privateconnect.dto.ConversationSeenRequest;

@Controller
public class WebSocketChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketChatController(
            MessageService messageService,
            SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/private-message")
    public void sendPrivateMessage(
            ChatMessageRequest request,
            Principal principal) {

        Message message = messageService.sendMessage(
                principal.getName(),
                request.receiverEmail(),
                request.content()
        );

        MessageResponse response = new MessageResponse(
                message.getId(),
                message.getSender().getEmail(),
                message.getReceiver().getEmail(),
                message.getContent(),
                message.getSentAt(),
                message.getStatus()
        );

        messagingTemplate.convertAndSendToUser(
                request.receiverEmail(),
                "/queue/messages",
                response
        );
    }

    @MessageMapping("/message-delivered")
    public void markDelivered(
            MessageStatusRequest request,
            Principal principal) {

        System.out.println("ACK RECEIVED!");
        System.out.println("Receiver: " + principal.getName());
        System.out.println("Message ID: " + request.messageId());

        messageService.markDelivered(
                request.messageId(),
                principal.getName()
        );

        System.out.println("DELIVERED UPDATED!");
    }


    @MessageMapping("/conversation-seen")
    public void markConversationSeen(
            ConversationSeenRequest request,
            Principal principal) {

        // Update messages in database
        messageService.markConversationSeen(
                request.senderEmail(),
                principal.getName()
        );

        // Notify original sender in real time
        messagingTemplate.convertAndSendToUser(
                request.senderEmail(),
                "/queue/seen",
                principal.getName()
        );

        System.out.println("SEEN notification sent to: "
                + request.senderEmail());
    }


}
