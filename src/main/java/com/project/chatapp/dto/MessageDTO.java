package com.project.chatapp.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private String sender;
    private String recipient;
    private String content;
    private LocalDateTime createdAt;

    public MessageDTO() {
    }

    public MessageDTO(String sender, String recipient, String content, LocalDateTime createdAt) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.createdAt = createdAt;
    }

    // getters / setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
