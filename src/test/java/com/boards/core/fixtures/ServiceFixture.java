package com.boards.core.fixtures;

import com.boards.core.model.dto.retroboard.*;
import com.boards.core.model.entities.retroboard.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceFixture {


    private ServiceFixture() {}
    public static final String RETRO_BOARD_ID = "retro-board-id";
    public static final String WALL_ID = "wall-id";
    public static final String WALL_STYLE_ID = "wall-style-id";
    public static final String STICKY_NOTE_STYLE_ID = "sticky-note-style-id";

    public static RetroBoardRequest buildRetroBoardRequest() {
        RetroBoardRequest retroBoardRequest = new RetroBoardRequest();
        retroBoardRequest.setId(RETRO_BOARD_ID);
        retroBoardRequest.setBlur("off");
        retroBoardRequest.setMaxLikes(5);
        retroBoardRequest.setName("Foo Bar");
        retroBoardRequest.setUserId("uid");
        return retroBoardRequest;
    }

    public static RetroBoard buildRetroBoard() {
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setBlur("off");
        retroBoard.setId(RETRO_BOARD_ID);
        retroBoard.setMaxLikes(5);
        retroBoard.setName("Foo Bar");
        retroBoard.setUserId("uid");
        return retroBoard;
    }

    public static void mockAuthentication() {
        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(buildLoggedInUser());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    public static User buildLoggedInUser() {
        User loggedInUser = new User();
        loggedInUser.setUid("uid");
        loggedInUser.setDisplayName("John Doe");
        loggedInUser.setEmail("johndoe@gmail.com");
        loggedInUser.setUsername("johndoe");
        return loggedInUser;
    }

    public static CreateRetroWallsRequest buildRetroWallsRequest() {
        CreateRetroWallsRequest retroWallsRequest = new CreateRetroWallsRequest();
        retroWallsRequest.setRetroBoardId(RETRO_BOARD_ID);

        RetroWallRequest retroWallRequest = buildRetroWallRequest();
        retroWallsRequest.setWalls(Arrays.asList(retroWallRequest));
        return retroWallsRequest;
    }

    public static RetroWallRequest buildRetroWallRequest() {
        var retroWallRequest = new RetroWallRequest();
        retroWallRequest.setRetroBoardId(RETRO_BOARD_ID);
        retroWallRequest.setSortCards(false);
        retroWallRequest.setTitle("Foo Bar");
        retroWallRequest.setWallId(WALL_ID);
        retroWallRequest.setWallOrder(1);
        retroWallRequest.setStyle(buildWallStyleRequest());
        return retroWallRequest;
    }

    public static WallStyleRequest buildWallStyleRequest() {
        WallStyleRequest wallStyleRequest = new WallStyleRequest();
        wallStyleRequest.setStickyNote(buildStickyNoteStyleRequest());
        return wallStyleRequest;
    }

    public static CreateStickyNoteStyleRequest buildStickyNoteStyleRequest() {
        CreateStickyNoteStyleRequest stickyNoteRequest = new CreateStickyNoteStyleRequest();
        stickyNoteRequest.setBackgroundColor("green");
        stickyNoteRequest.setLikeBtnPosition("right");
        stickyNoteRequest.setNoteStyleId("note-style-id");
        stickyNoteRequest.setTextColor("white");
        return stickyNoteRequest;
    }

    public static RetroWall buildPersistedRetroWall() {
        RetroWall persistedRetroWall = new RetroWall();
        persistedRetroWall.setRetroBoardId(RETRO_BOARD_ID);
        persistedRetroWall.setSortCards(false);
        persistedRetroWall.setTitle("Foo Bar");
        persistedRetroWall.setWallId(WALL_ID);
        persistedRetroWall.setWallOrder(1);
        persistedRetroWall.setWallStyle(WALL_STYLE_ID);
        return persistedRetroWall;
    }

    public static StickyNoteStyle buildPersistedStickyNoteStyle() {
        var stickyNoteStyle = new StickyNoteStyle();
        stickyNoteStyle.setBackgroundColor("crimson");
        stickyNoteStyle.setLikeBtnPosition("right");
        stickyNoteStyle.setTextColor("white");
        stickyNoteStyle.setWallStyleId(WALL_STYLE_ID);
        stickyNoteStyle.setStickyNoteStyleId(STICKY_NOTE_STYLE_ID);
        return stickyNoteStyle;
    }

    public static WallStyle buildPersistedWallStyle() {
        var wallStyle = new WallStyle();
        wallStyle.setWallId(WALL_ID);
        wallStyle.setWallStyleId(WALL_STYLE_ID);
        return wallStyle;
    }
}
