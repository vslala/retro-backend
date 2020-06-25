package com.boards.core.services;

import com.boards.core.fixtures.ServiceFixture;
import com.boards.core.model.dto.retroboard.CreateResponse;
import com.boards.core.model.dto.retroboard.RetroBoardRequest;
import com.boards.core.model.entities.retroboard.RetroBoard;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.repositories.retroboard.NoteRepository;
import com.boards.core.model.repositories.retroboard.RetroBoardRepository;
import com.boards.core.model.repositories.retroboard.RetroWallRepository;
import com.boards.core.model.repositories.shareitems.SharedItemRepository;
import com.boards.core.model.repositories.teams.TeamMemberRepository;
import com.boards.core.model.repositories.teams.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.boards.core.fixtures.ServiceFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RetroBoardServiceTest {

    private RetroBoardRepository retroBoardRepository;
    private RetroBoardService retroBoardService;
    private SharedItemRepository sharedItemRepository;
    private TeamRepository teamRepository;
    private RetroWallRepository retroWallRepository;
    private NoteRepository noteRepository;

    @BeforeEach
    public void setup() {
        retroBoardRepository = mock(RetroBoardRepository.class);
        sharedItemRepository = mock(SharedItemRepository.class);
        teamRepository = mock(TeamRepository.class);
        TeamMemberRepository teamMemberRepository = mock(TeamMemberRepository.class);
        retroWallRepository = mock(RetroWallRepository.class);
        noteRepository = mock(NoteRepository.class);
        retroBoardService = new RetroBoardService(retroBoardRepository, sharedItemRepository,
                teamRepository, teamMemberRepository, retroWallRepository, noteRepository);
        mockAuthentication();
    }

    @Test
    public void itShouldCreateRetroBoard() {
        when(retroBoardRepository.save(any(RetroBoard.class))).thenReturn(buildRetroBoard());
        CreateResponse response = retroBoardService.createRetroBoard(buildRetroBoardRequest());

        assertTrue(response.getResourceUrl().contains("/retro-board/"));
        assertEquals("/retro-board/retro-board-id", response.getResourceUrl());
    }

    @Test
    public void itShouldGetRetroBoardIfRequestedByTheOwnerOfTheBoard() {
        when(retroBoardRepository.findById(RETRO_BOARD_ID)).thenReturn(Optional.of(buildRetroBoard()));

        Optional<RetroBoard> retroBoard = retroBoardService.getRetroBoard(buildLoggedInUser(), RETRO_BOARD_ID);
        assertTrue(retroBoard.isPresent());
    }

    @Test
    public void itShouldGetAllRetroBoards() {
        when(retroBoardRepository.findAllByUserId("uid")).thenReturn(Arrays.asList(buildRetroBoard()));

        List<RetroBoard> retroBoards = retroBoardService.getRetroBoards();

        assertFalse(retroBoards.isEmpty());
        assertEquals(1, retroBoards.size());
    }

    @Test
    public void itShouldUpdateRetroBoardInfo() {
        when(retroBoardRepository.save(any(RetroBoard.class))).thenReturn(buildRetroBoard());
        URI uri = retroBoardService.updateRetroBoard(buildRetroBoardRequest());

        assertNotNull(uri);
        assertEquals("/retro-board/" + RETRO_BOARD_ID, uri.toString());
    }

    @Test
    public void itShouldDeleteRetroBoard() {
        when(retroBoardRepository.findById(RETRO_BOARD_ID)).thenReturn(Optional.of(buildRetroBoard()));
        retroBoardService.deleteBoard(buildLoggedInUser(), RETRO_BOARD_ID);
        verify(noteRepository, times(1)).deleteAllByWallIdIn(Collections.emptyList());
        verify(retroBoardRepository, times(1)).deleteById(RETRO_BOARD_ID);
    }

}