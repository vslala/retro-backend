package com.boards.core.services;

import com.boards.core.model.dto.user.UserRequestByEmail;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.repositories.retroboard.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j
@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByEmail(UserRequestByEmail userRequestByEmail) {
        Optional<User> user = userRepository.findByEmail(userRequestByEmail.getEmail());
        return user;
    }
}
