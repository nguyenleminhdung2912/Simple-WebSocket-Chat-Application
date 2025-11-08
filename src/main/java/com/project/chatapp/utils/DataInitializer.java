package com.project.chatapp.utils;

import com.project.chatapp.model.User;
import com.project.chatapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (userRepository.count() == 0) {
                userRepository.save(new User(null, "admin", encoder.encode("123"), "Admin", true));
                userRepository.save(new User(null, "john", encoder.encode("123"), "John Doe", true));
                userRepository.save(new User(null, "emily", encoder.encode("123"), "Emily Tin", true));
                userRepository.save(new User(null, "chris", encoder.encode("123"), "Chris Barne", true));
                userRepository.save(new User(null, "vicky", encoder.encode("123"), "Vicky Star", true));
            }
        };
    }
}
