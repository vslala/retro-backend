package com.boards.core.controllers;

import com.boards.core.model.entities.RetroBoard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RetroBoardControllerTest {

    @BeforeAll
    static void setup() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:h2:mem:db;MODE=MySQL;DB_CLOSE_DELAY=-1", "vslala", "simplepass")
                .load();
        for (Location location : flyway.getConfiguration().getLocations()) {
            System.out.println("Location: " + location);
        }
        flyway.migrate();
    }

    @Autowired
    private MockMvc mockMvc;

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
    void persist_retro_board_in_the_database() throws Exception {
        String secureToken = this.generateSecureToken();
        System.out.println("ID TOKEN: \n" + secureToken);

        mockMvc.perform(
                post("/retro-board")
                        .header("Authorization", "Bearer " + secureToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(Map.of(
                                "name", "Test Board",
                                "maxLikes", 5))))
                .andExpect(status().isCreated());


    }

    public static byte[] asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
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