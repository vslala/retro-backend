package com.boards.core.controllers;

import com.boards.core.ex.ResourceNotFoundException;
import com.boards.core.ex.UnauthorizedUser;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.lang.String.format;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

@Log4j
@ControllerAdvice
public class GlobalControllerHandler {

    @ExceptionHandler(UnauthorizedUser.class)
    public ResponseEntity<HttpStatus> handleUnauthorizedUser(UnauthorizedUser ex) {
        log.warn(format("Unauthorized user tried accessing %s", ex.getMessage()));
        return status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpStatus> handleResourceNotFound(ResourceNotFoundException e) {
        log.warn(format("Resource Not Found. Message = %s", e.getMessage()));
        return notFound().build();
    }
}
