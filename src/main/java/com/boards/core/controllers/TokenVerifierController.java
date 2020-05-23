package com.boards.core.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin
@Log4j
@RestController
@RequestMapping("/token")
public class TokenVerifierController {

    @GetMapping("/verify")
    public ResponseEntity<HttpStatus> verifyToken(@RequestParam("id_token") String idTokenString) {
        if (Objects.isNull(idTokenString)) return ResponseEntity.badRequest().build();

        FirebaseToken idToken = null;
        try {
            idToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
        } catch (FirebaseAuthException e) {
            log.info("Invalid token!");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .header("Authorization", idTokenString)
                .build();
    }
}
