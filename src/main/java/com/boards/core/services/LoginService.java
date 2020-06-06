package com.boards.core.services;

import com.boards.core.model.dto.retroboard.UserRequest;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.repositories.retroboard.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j
@Service
public class LoginService {

    private UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void recordLogin(UserRequest userRequest) {
        Optional<User> user = userRepository.findById(userRequest.getUid());
        if (user.isEmpty())
            userRepository.save(userRequest.createUser());
    }
}
