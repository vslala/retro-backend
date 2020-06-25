package com.boards.core.fixtures;

import com.boards.core.model.dto.retroboard.RetroBoardRequest;
import com.boards.core.model.entities.retroboard.RetroBoard;
import com.boards.core.model.entities.retroboard.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceFixture {

    private ServiceFixture() {}
    public static final String RETRO_BOARD_ID = "retro-board-id";

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
}
