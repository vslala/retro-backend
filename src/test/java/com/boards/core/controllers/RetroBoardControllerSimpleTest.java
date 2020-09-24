package com.boards.core.controllers;

import com.boards.core.fixtures.ServiceFixture;
import com.boards.core.model.dto.retroboard.CreateResponse;
import com.boards.core.model.dto.retroboard.RetroBoardRequest;
import com.boards.core.services.RetroBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetroBoardControllerSimpleTest {

    private RetroBoardService retroBoardService = mock(RetroBoardService.class);
    private SimpMessagingTemplate simpTemplate = mock(SimpMessagingTemplate.class);
    private RetroBoardController retroBoardController;

    @BeforeEach
    public void setup() {
        mockSecurityContext();
        retroBoardController = new RetroBoardController(retroBoardService, simpTemplate);
    }

    private void mockSecurityContext() {
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(ServiceFixture.buildLoggedInUser());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void itShouldPersistRetroBoardSuccessfullyAndGiveStatus201WithTheLinkToTheBoard() {
        // Given
        RetroBoardRequest retroBoardRequest = ServiceFixture.buildRetroBoardRequest();
        CreateResponse createResponse = ServiceFixture.buildCreateResponse(retroBoardRequest.getId());

        // WHEN
        when(retroBoardService.createRetroBoard(retroBoardRequest)).thenReturn(createResponse);
        var response = retroBoardController.createRetroBoard(retroBoardRequest);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(createResponse, response.getBody());
    }

    @Test
    void itShouldGetTheRetroBoardById() {

        when(retroBoardService.getRetroBoard(ServiceFixture.buildLoggedInUser(), "retro-board-id"))
                .thenReturn(Optional.of(ServiceFixture.buildRetroBoard()));
        var response = retroBoardController.get("retro-board-id");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void itShouldGetAllBoards() {
        when(retroBoardService.getRetroBoards()).thenReturn(Arrays.asList(ServiceFixture.buildRetroBoard()));
        var response = retroBoardController.getBoards();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }
}
