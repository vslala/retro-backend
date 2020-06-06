package com.boards.core.controllers;

import com.boards.core.model.dto.user.UserRequestByEmail;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.services.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.boards.core.configuration.AppConfig.*;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Log4j
@CrossOrigin(exposedHeaders = {EXPOSE_ACCESS_TOKEN, EXPOSE_LOCATION, EXPOSE_UID})
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> getUserByEmail(@Valid @ModelAttribute UserRequestByEmail userRequestByEmail) {
        Optional<User> user = userService.getUserByEmail(userRequestByEmail);
        return user.isPresent() ? ok(user.get()) : notFound().build();
    }
}
