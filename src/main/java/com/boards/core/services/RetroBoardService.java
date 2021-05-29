package com.boards.core.services;

import com.boards.core.configuration.AppUtil;
import com.boards.core.ex.ResourceNotFoundException;
import com.boards.core.ex.UnauthorizedUser;
import com.boards.core.model.dto.retroboard.CreateResponse;
import com.boards.core.model.dto.retroboard.RetroBoardRequest;
import com.boards.core.model.entities.retroboard.RetroBoard;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.shareitems.SharedItem;
import com.boards.core.model.entities.teams.TeamMemberTeamMapping;
import com.boards.core.model.repositories.retroboard.NoteRepository;
import com.boards.core.model.repositories.retroboard.RetroBoardRepository;
import com.boards.core.model.repositories.retroboard.RetroWallRepository;
import com.boards.core.model.repositories.retroboard.UserRepository;
import com.boards.core.model.repositories.shareitems.SharedItemRepository;
import com.boards.core.model.repositories.teams.TeamMemberRepository;
import com.boards.core.model.repositories.teams.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.net.URI.create;

@Log4j
@Service
@RequiredArgsConstructor
public class RetroBoardService {

    private final RetroBoardRepository retroBoardRepository;
    private final SharedItemRepository sharedItemRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final RetroWallRepository retroWallRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public CreateResponse createRetroBoard(RetroBoardRequest input) {
        RetroBoard retroBoard = input.createRetroBoard();
        RetroBoard persist = retroBoardRepository.save(retroBoard);

        return CreateResponse.builder().resourceUrl(
                create(String.format("/retro-board/%s", persist.getId())).toString())
                .build();
    }

    @SneakyThrows
    public Optional<RetroBoard> getRetroBoard(User loggedInUser, String retroBoardId) {
        Optional<RetroBoard> persistedRetroBoard = retroBoardRepository.findById(retroBoardId);
        if (persistedRetroBoard.isEmpty()) throw new ResourceNotFoundException("Retro Board Not Found!");

        // if the board is requested by its owner
        if (persistedRetroBoard.get().getUserId().equals(loggedInUser.getUid()))
            return persistedRetroBoard;

        // or the board is created by anonymous user then return the board
        Optional<User> userWhoCreatedTheBoard = userRepository.findById(persistedRetroBoard.get().getUserId());
        if (userWhoCreatedTheBoard.isEmpty() || !userWhoCreatedTheBoard.get().isEmailVerified())
            return persistedRetroBoard;

        List<TeamMemberTeamMapping> userTeamMappings = teamMemberRepository.findAllByUid(loggedInUser.getUid());
        List<SharedItem> sharedItems = sharedItemRepository.findAllByItemId(retroBoardId);

        // check if the loggedInUser is allowed to view the board
        return userTeamMappings.stream().filter(userTeamMapping ->
                sharedItems.stream()
                        .anyMatch(sharedItem ->
                                sharedItem.getTeamId().equals(userTeamMapping.getTeamId())))
                .findFirst().map(teamMember -> retroBoardRepository.findById(retroBoardId))
                .orElseThrow(() -> new UnauthorizedUser("User not authorized to view the board!"));
    }

    @SneakyThrows
    public List<RetroBoard> getRetroBoards() {
        return retroBoardRepository.findAllByUserId(AppUtil.getLoggedInUser().getUid());
    }

    public URI updateRetroBoard(RetroBoardRequest retroBoardRequest) {
        RetroBoard retroBoard = retroBoardRepository.save(retroBoardRequest.updateRetroBoard());
        return create("/retro-board/" + retroBoard.getId());
    }

    @Transactional
    public void deleteBoard(User loggedInUser, String retroBoardId) {
        Optional<RetroBoard> retroBoard = retroBoardRepository.findById(retroBoardId);
        retroBoard.ifPresent(board -> {
            if (board.getUserId().equals(loggedInUser.getUid())) {
                noteRepository.deleteAllByWallIdIn(
                        retroWallRepository.findAllByRetroBoardId(retroBoardId)
                                .stream()
                                .map(wall -> wall.getWallId())
                                .collect(Collectors.toList()));
                retroWallRepository.deleteByRetroBoardId(retroBoardId);
                retroBoardRepository.deleteById(board.getId());
            }
        });
    }
}
