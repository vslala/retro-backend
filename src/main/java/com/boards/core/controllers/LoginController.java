package com.boards.core.controllers;

import com.boards.core.model.dto.retroboard.UserRequest;
import com.boards.core.services.LoginService;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.boards.core.configuration.AppConfig.EXPOSE_ACCESS_TOKEN;
import static com.boards.core.configuration.AppConfig.EXPOSE_LOCATION;

@Log4j
@CrossOrigin(exposedHeaders = {EXPOSE_ACCESS_TOKEN, EXPOSE_LOCATION})
@RestController
@RequestMapping("/login")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> loginUser(@RequestBody UserRequest userRequest) {
        loginService.recordLogin(userRequest);
        log.info("Login Successful!");
        return ResponseEntity.ok().build();
    }
}
