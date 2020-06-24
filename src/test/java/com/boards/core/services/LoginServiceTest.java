package com.boards.core.services;

import com.boards.core.model.dto.retroboard.UserRequest;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.repositories.retroboard.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {


    private UserRepository userRepository;

    private LoginService loginService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        loginService = new LoginService(userRepository);
    }

    @Test
    public void itShouldRecordLoginInfoOfTheUser() {
        mockFindByUserId();

        UserRequest userRequest = buildUserRequest();
        loginService.recordLogin(userRequest);

        verify(userRepository, times(1));
    }

    private UserRequest buildUserRequest() {
        var userRequest = new UserRequest();
        userRequest.setDisplayName("John Doe");
        userRequest.setEmail("johndoe@gmail.com");
        userRequest.setUid("uid");
        return userRequest;
    }

    private void mockFindByUserId() {
        var user = new User();
        user.setUsername("johndoe");
        user.setEmail("johndoe@gmail.com");
        user.setDisplayName("John Doe");
        user.setUid("uid");
        when(userRepository.findById("uid")).thenReturn(Optional.of(user));
    }
}