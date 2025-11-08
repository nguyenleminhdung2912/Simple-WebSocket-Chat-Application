package com.project.chatapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String sender;

    private String recipient;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    private boolean read = false;

    public Message(String sender, String recipient, String content, LocalDateTime now) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.createdAt = now;
        this.read = false;
    }
}
