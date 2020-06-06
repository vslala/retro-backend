package com.boards.core.services;

import com.boards.core.ex.ResourceNotFoundException;
import com.boards.core.ex.UnauthorizedUser;
import com.boards.core.model.dto.shareditems.SharedItemRequest;
import com.boards.core.model.dto.shareditems.SharedItemResponse;
import com.boards.core.model.entities.retroboard.RetroBoard;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.shareitems.SharedItem;
import com.boards.core.model.repositories.retroboard.RetroBoardRepository;
import com.boards.core.model.repositories.shareitems.SharedItemRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.net.URI.create;

@Log4j
@Service
public class ShareService {

    private SharedItemRepository sharedItemRepository;
    private RetroBoardRepository retroBoardRepository;

    public ShareService(SharedItemRepository sharedItemRepository, RetroBoardRepository retroBoardRepository) {
        this.sharedItemRepository = sharedItemRepository;
        this.retroBoardRepository = retroBoardRepository;
    }

    public URI shareItem(User loggedInUser, SharedItemRequest sharedItemRequest) {
        Optional<RetroBoard> persistedRetroBoard = retroBoardRepository.findById(sharedItemRequest.getItemId());
        return persistedRetroBoard.map(retroBoard -> {
            if (!retroBoard.getUserId().equals(loggedInUser.getUid()))
                throw new UnauthorizedUser("User is not authorized!");

            Iterable<SharedItem> persistedSharedItems = sharedItemRepository.saveAll(sharedItemRequest.createSharedItems());

            return create(String.format("/share/%s", sharedItemRequest.getItemId()));
        }).orElseThrow(() -> new ResourceNotFoundException("Shared Item is not found!"));
    }

    public SharedItemResponse getSharedItems(String itemId) {
        List<SharedItem> sharedItems = sharedItemRepository.findAllByItemId(itemId);
        return SharedItemResponse.createResponse(itemId, sharedItems);
    }
}
