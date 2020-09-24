package com.boards.core.services;

import com.boards.core.model.dto.retroboard.RetroWallsResponse;
import com.boards.core.model.repositories.retroboard.RetroBoardRepository;
import com.boards.core.model.repositories.retroboard.RetroWallRepository;
import com.boards.core.model.repositories.retroboard.StickyNoteStyleRepository;
import com.boards.core.model.repositories.retroboard.WallStyleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import static com.boards.core.fixtures.ServiceFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RetroWallsServiceTest {

    private RetroWallRepository retroWallRepository;
    private StickyNoteStyleRepository stickyNoteStyleRepository;
    private WallStyleRepository wallStyleRepository;
    private RetroBoardRepository retroBoardRepository;
    private RetroWallsService retroWallsService;

    @BeforeEach
    public void setup() {
        retroWallRepository = mock(RetroWallRepository.class);
        stickyNoteStyleRepository = mock(StickyNoteStyleRepository.class);
        wallStyleRepository = mock(WallStyleRepository.class);
        retroBoardRepository = mock(RetroBoardRepository.class);
        retroWallsService = new RetroWallsService(retroWallRepository, stickyNoteStyleRepository,
                wallStyleRepository, retroBoardRepository);
    }

    @Test
    public void itShouldReturnUriForTheBoardIfTheWallsHaveBeenInitializedBefore() {
        when(retroWallRepository.findAllByRetroBoardId(RETRO_BOARD_ID)).thenReturn(Arrays.asList(buildPersistedRetroWall()));

        URI uri = retroWallsService.createWalls(buildRetroWallsRequest());

        assertEquals("/retro-board/walls/" + RETRO_BOARD_ID, uri.toString());
    }

    @Test
    public void itShouldCreateRetroWallsAndReturnTheUri() {
        when(retroWallRepository.findAllByRetroBoardId(RETRO_BOARD_ID)).thenReturn(Collections.emptyList());
        when(stickyNoteStyleRepository.saveAll(anyList())).thenReturn(Arrays.asList(buildPersistedStickyNoteStyle()));
        when(retroWallRepository.saveAll(anyList())).thenReturn(Arrays.asList(buildPersistedRetroWall()));
        when(wallStyleRepository.saveAll(anyList())).thenReturn(Arrays.asList(buildPersistedWallStyle()));

        URI uri = retroWallsService.createWalls(buildRetroWallsRequest());

        assertEquals("/retro-board/walls/" + RETRO_BOARD_ID, uri.toString());
    }

    @Test
    public void itShouldGetRetroWallsResponseByRetroBoardId() {
        when(retroWallRepository.findAllByRetroBoardId(RETRO_BOARD_ID)).thenReturn(Arrays.asList(buildPersistedRetroWall()));
        when(wallStyleRepository.findAllByWallIdIn(Arrays.asList(WALL_ID))).thenReturn(Arrays.asList(buildPersistedWallStyle()));
        when(stickyNoteStyleRepository.findAllByWallStyleIdIn(Arrays.asList(WALL_STYLE_ID))).thenReturn(Arrays.asList(buildPersistedStickyNoteStyle()));

        RetroWallsResponse response = retroWallsService.getWallsForBoard(RETRO_BOARD_ID);

        assertEquals(RETRO_BOARD_ID, response.getRetroBoardId());
        assertEquals(1, response.getWalls().size());
        assertEquals("right", response.getWalls().get(0).getStyle().getStickyNote().getLikeBtnPosition());
    }

}