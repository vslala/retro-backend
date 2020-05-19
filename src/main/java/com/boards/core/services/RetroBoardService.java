package com.boards.core.services;

import com.boards.core.model.CreateResponse;
import com.boards.core.model.RetroBoard;
import com.boards.core.service.CacheManagerImpl;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static java.net.URI.create;

@Log4j
@Service
public class RetroBoardService {

    private CacheManagerImpl<RetroBoard> cacheManager;

    @Autowired
    public RetroBoardService(CacheManagerImpl<RetroBoard> cacheManager) {
        this.cacheManager = cacheManager;
    }

    public CreateResponse createRetroBoard(RetroBoard retroBoard) {
        String retroBoardId = "-M0QZpAXh5sQqawVkrrM"; // save the board and get the id
        return CreateResponse.builder().resourceUrl(
                create(String.format("/retro-board/%s", retroBoardId)).toString())
                .build();
    }

    @SneakyThrows
    public Optional<RetroBoard> getRetroBoard(String retroBoardId) {
        List<RetroBoard> retroBoardList = cacheManager.readCache(Paths.get("src/main/resources/cache/boards").toFile(), RetroBoard.class).result();
        return retroBoardList.stream().filter(board -> board.getId().equals(retroBoardId)).findFirst();
    }

    @SneakyThrows
    public List<RetroBoard> getRetroBoards() {
        List<RetroBoard> retroBoardList = cacheManager.readCache(Paths.get("src/main/resources/cache/boards").toFile(), RetroBoard.class).result();
        return retroBoardList;
    }
}
