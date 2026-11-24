
package com.shashank.privateconnect.service;

import com.shashank.privateconnect.entity.Message;
import com.shashank.privateconnect.entity.User;
import com.shashank.privateconnect.repository.MessageRepository;
import com.shashank.privateconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(
            MessageRepository messageRepository,
            UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message sendMessage(
            String senderEmail,
            String receiverEmail,
            String content) {

        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        return messageRepository.save(message);
    }

    public List<Message> getConversation(
            String email1, String email2) {

        User user1 = userRepository.findByEmail(email1)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user2 = userRepository.findByEmail(email2)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return messageRepository
                .findBySenderAndReceiverOrReceiverAndSenderOrderBySentAtAsc(
                        user1, user2, user1, user2
                );
    }

    public void updateStatus(Long messageId, String status) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setStatus(status);
        messageRepository.save(message);
    }

    @Transactional
    public void markDelivered(Long messageId, String receiverEmail) {

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getReceiver().getEmail().equals(receiverEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        message.setStatus("DELIVERED");
        messageRepository.save(message);
    }

    @Transactional
    public void markConversationSeen(String senderEmail, String receiverEmail) {

        List<Message> messages = messageRepository
                .findBySenderAndReceiverOrReceiverAndSenderOrderBySentAtAsc(
                        userRepository.findByEmail(senderEmail).orElseThrow(),
                        userRepository.findByEmail(receiverEmail).orElseThrow(),
                        userRepository.findByEmail(senderEmail).orElseThrow(),
                        userRepository.findByEmail(receiverEmail).orElseThrow()
                );

        for (Message message : messages) {
            if (message.getSender().getEmail().equals(senderEmail)
                    && message.getReceiver().getEmail().equals(receiverEmail)) {

                message.setStatus("SEEN");
                message.setRead(true);
            }
        }

        messageRepository.saveAll(messages);
    }

}
