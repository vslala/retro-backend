package com.boards.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class ApplicationConfigurationBeans {

    @Bean
    public FirebaseApp firebaseApp() {
        FileInputStream serviceAccount;
        FirebaseOptions options = null;
        try {
            serviceAccount = new FileInputStream("src/main/resources/retro-board-ebce6-firebase-adminsdk-ex8kt-905b8c139f.json");
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
