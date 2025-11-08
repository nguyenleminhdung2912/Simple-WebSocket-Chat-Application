package com.project.chatapp.controller;

import com.project.chatapp.dto.MessageDTO;
import com.project.chatapp.model.Message;
import com.project.chatapp.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;

    @MessageMapping("/chat")
    public void processMessage(MessageDTO msg, Principal principal) {
        // ensure sender is authenticated user
        String sender = principal != null ? principal.getName() : msg.getSender();
        Message m = new Message(sender, msg.getRecipient(), msg.getContent(), LocalDateTime.now());
        messageRepository.save(m);

        MessageDTO out = new MessageDTO(m.getSender(), m.getRecipient(), m.getContent(), m.getCreatedAt());

        // send to recipient and sender's user queue
        messagingTemplate.convertAndSendToUser(m.getRecipient(), "/queue/messages", out);
        messagingTemplate.convertAndSendToUser(m.getSender(), "/queue/messages", out);
    }
}
