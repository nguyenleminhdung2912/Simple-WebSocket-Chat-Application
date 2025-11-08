package com.project.chatapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatPageController {

    @GetMapping("/chat")
    public String chatPage(@RequestParam String with,
                           @AuthenticationPrincipal UserDetails user,
                           Model model) {
        model.addAttribute("me", user.getUsername());
        model.addAttribute("withUser", with);
        return "chat";
    }
}
