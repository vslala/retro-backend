package com.boards.core.services;

import com.boards.core.model.dto.CreateResponse;
import com.boards.core.model.dto.CreateRetroBoardRequest;
import com.boards.core.model.entities.RetroBoard;
import com.boards.core.model.repositories.RetroBoardRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.net.URI.create;

@Log4j
@Service
public class RetroBoardService {

    private RetroBoardRepository retroBoardRepository;

    @Autowired
    public RetroBoardService(RetroBoardRepository retroBoardRepository) {
        this.retroBoardRepository = retroBoardRepository;
    }

    public CreateResponse createRetroBoard(CreateRetroBoardRequest input) {
        RetroBoard retroBoard = RetroBoard.newInstance(input.getName(), input.getMaxLikes());
        retroBoard.generateUniqId(); // generates uniq id for itself
        RetroBoard persist = retroBoardRepository.save(retroBoard);

        return CreateResponse.builder().resourceUrl(
                create(String.format("/retro-board/%s", persist.getId())).toString())
                .build();
    }

    @SneakyThrows
    public Optional<RetroBoard> getRetroBoard(String retroBoardId) {
        Optional<RetroBoard> retroBoard = retroBoardRepository.findById(retroBoardId);
        return retroBoard;
    }

    @SneakyThrows
    public List<RetroBoard> getRetroBoards() {
        return retroBoardRepository.findAll();
    }
}
