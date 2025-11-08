package com.project.chatapp.controller;

import com.project.chatapp.model.Message;
import com.project.chatapp.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final MessageRepository messageRepository;

    // GET /messages?with=bob
    @GetMapping("/messages")
    public List<Message> getConversation(@AuthenticationPrincipal UserDetails user, @RequestParam String with) {
        String me = user.getUsername();
        return messageRepository.findConversation(me, with);
    }
}
