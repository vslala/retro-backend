package com.boards.core.controllers;

import com.boards.core.model.RetroBoard;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RetroBoardControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int randomPort;

    private String getTestUrl(String path) {
        return String.format("http://localhost:%d/%s", randomPort, path);
    }

    @Test
    void get_retro_board_by_id() throws IOException, FirebaseAuthException {
        String secureToken = this.generateSecureToken();
        System.out.println("ID TOKEN: \n" + secureToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + secureToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String path = "/retro-board/-M0QZpAXh5sQqawVkrrM";
        ResponseEntity<RetroBoard> retroBoard = restTemplate.exchange(path, HttpMethod.GET, entity, RetroBoard.class);
        assertNotNull(retroBoard.getBody());
        assertEquals("-M0QZpAXh5sQqawVkrrM", retroBoard.getBody().getId());
    }

    @Test
    void get_all_retro_boards() {
        RetroBoard[] retroBoardList = restTemplate.getForObject(getTestUrl("/retro-board"), RetroBoard[].class);
        assertTrue(retroBoardList.length > 0);
    }

    @Test
    void persist_retro_board_in_the_database() throws FirebaseAuthException, IOException {
        String secureToken = this.generateSecureToken();
        System.out.println("ID TOKEN: \n" + secureToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + secureToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(Map.of(
                "name", "Test Board",
                "maxLikes", 5)
                , headers);

        ResponseEntity<String> response = restTemplate.exchange(getTestUrl("/retro-board"), HttpMethod.POST, entity, String.class);
        System.out.println("Response\b" + response.getBody());

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response);
    }

    private String generateSecureToken() throws FirebaseAuthException, IOException {
        String idToken = null;
        String customToken = FirebaseAuth.getInstance().createCustomToken("foo bar");
        System.out.println("CUSTOM TOKEN: \n" + customToken);
        final String ID_TOOLKIT_URL =
                "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken";
        final JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
        final HttpTransport transport = Utils.getDefaultTransport();
        final String FIREBASE_API_KEY = "AIzaSyAERXDD08sF5pEmX1j3nlxjiermP5mvVrM";

        GenericUrl url = new GenericUrl(ID_TOOLKIT_URL + "?key="
                + FIREBASE_API_KEY);
        Map<String, Object> content = ImmutableMap.of(
                "token", customToken, "returnSecureToken", true);
        com.google.api.client.http.HttpRequest request = transport.createRequestFactory().buildPostRequest(url,
                new JsonHttpContent(jsonFactory, content));
        ((com.google.api.client.http.HttpRequest) request).setParser(new JsonObjectParser(jsonFactory));
        com.google.api.client.http.HttpResponse response = request.execute();
        try {
            GenericJson json = response.parseAs(GenericJson.class);
            idToken = json.get("idToken").toString();
        } finally {
            response.disconnect();
        }



        return idToken;
    }
}