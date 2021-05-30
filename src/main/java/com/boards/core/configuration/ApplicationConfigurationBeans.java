package com.boards.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Log4j
@Configuration
public class ApplicationConfigurationBeans {

    @Value("${credentials}")
    private String credentials;

    @Bean
    public FirebaseApp firebaseApp() {
        FirebaseOptions options = null;
        try {
            InputStream serviceAccount = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(credentials));
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://retro-board-ebce6.firebaseio.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FirebaseApp.initializeApp(options);

    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
