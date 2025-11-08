package com.project.chatapp.repository;

import com.project.chatapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipientOrRecipientAndSenderOrderByCreatedAtAsc(String s1, String r1, String s2, String r2);

    @Query("SELECT m FROM Message m WHERE (m.sender = :u1 AND m.recipient = :u2) OR (m.sender = :u2 AND m.recipient = :u1) ORDER BY m.createdAt ASC")
    List<Message> findConversation(@Param("u1") String u1, @Param("u2") String u2);
}
