package com.boards.core.controllers;

import com.boards.core.RetroBoardMother;
import com.boards.core.model.entities.retroboard.RetroBoard;
import com.google.firebase.auth.FirebaseAuthException;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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

import static com.boards.core.RetroBoardMother.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
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
    void get_retro_board_by_id() throws Exception {
        String secureToken = RetroBoardMother.generateSecureToken();
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
        String secureToken = RetroBoardMother.generateSecureToken();
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


}