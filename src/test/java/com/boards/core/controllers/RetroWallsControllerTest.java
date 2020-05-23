package com.boards.core.controllers;

import com.boards.core.RetroBoardMother;
import com.boards.core.model.dto.CreateRetroWallRequest;
import com.boards.core.model.dto.CreateRetroWallsRequest;
import com.boards.core.model.dto.CreateStickyNoteStyleRequest;
import com.boards.core.model.dto.CreateWallStyleRequest;
import com.boards.core.model.entities.RetroBoard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static com.boards.core.RetroBoardMother.asJsonString;
import static com.boards.core.RetroBoardMother.generateSecureToken;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RetroWallsControllerTest {

    @LocalServerPort
    int randomPort;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void it_should_create_retro_walls_for_the_given_retro_board() throws Exception {
        System.out.println("Local Port: " + randomPort);
        String retroBoardUrl = createRetroBoard();
        RetroBoard retroBoard = getRetroBoard(retroBoardUrl);

        CreateStickyNoteStyleRequest stickyNoteStyle = new CreateStickyNoteStyleRequest();
        stickyNoteStyle.setBackgroundColor("Black");
        stickyNoteStyle.setLikeBtnPosition("left");
        stickyNoteStyle.setTextColor("White");

        CreateWallStyleRequest retroWallStyle = new CreateWallStyleRequest();
        retroWallStyle.setStickyNote(stickyNoteStyle);

        CreateRetroWallRequest retroWallRequest = new CreateRetroWallRequest();
        retroWallRequest.setRetroBoardId(retroBoard.getId());
        retroWallRequest.setTitle("Went Well");
        retroWallRequest.setSortCards(false);
        retroWallRequest.setStyle(retroWallStyle);

        CreateRetroWallsRequest retroWallsRequest = new CreateRetroWallsRequest();
        retroWallsRequest.setRetroBoardId(retroBoard.getId());
        retroWallsRequest.setWalls(List.of(retroWallRequest));


        String responseUrl = mockMvc.perform(post("/retro-board/walls")
                .header("Authorization", "Bearer " + generateSecureToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(retroWallsRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        assertNotNull(responseUrl);
        assertTrue(responseUrl.contains("/retro-board/walls"));
    }

    private RetroBoard getRetroBoard(String retroBoardUrl) throws Exception {
        String retroBoardJson = mockMvc.perform(get(retroBoardUrl)
                .header("Authorization", "Bearer " + generateSecureToken())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return new ObjectMapper().readValue(retroBoardJson, RetroBoard.class);
    }

    private String createRetroBoard() throws Exception {
        String secureToken = RetroBoardMother.generateSecureToken();
        System.out.println("ID TOKEN: \n" + secureToken);

        String retroBoardUrl = (String) mockMvc.perform(
                post("/retro-board")
                        .header("Authorization", "Bearer " + secureToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(Map.of(
                                "name", "Test Board",
                                "maxLikes", 5))))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andReturn().getResponse().getHeaderValue("Location");

        System.out.println("Location: " + retroBoardUrl);
        return retroBoardUrl;
    }
}