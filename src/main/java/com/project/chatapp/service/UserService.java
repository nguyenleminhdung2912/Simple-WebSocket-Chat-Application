package com.project.chatapp.service;

import com.project.chatapp.model.User;
import com.project.chatapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User register(String username, String rawPassword, String displayName) {
        if (userRepository.findByUsername(username).isPresent()) throw new IllegalArgumentException("Username exists");
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setDisplayName(displayName);
        return userRepository.save(u);
    }


    public List<User> listOtherUsers(String me) {
        return userRepository.findAll().stream()
                .filter(u -> !u.getUsername().equals(me))
                .collect(Collectors.toList());
    }
}
