package com.boards.core.services;

import com.boards.core.model.dto.CreateRetroWallRequest;
import com.boards.core.model.dto.CreateRetroWallsRequest;
import com.boards.core.model.dto.CreateStickyNoteStyleRequest;
import com.boards.core.model.dto.RetroWallsResponse;
import com.boards.core.model.entities.RetroBoard;
import com.boards.core.model.entities.RetroWall;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.entities.WallStyle;
import com.boards.core.model.repositories.RetroBoardRepository;
import com.boards.core.model.repositories.RetroWallRepository;
import com.boards.core.model.repositories.StickyNoteStyleRepository;
import com.boards.core.model.repositories.WallStyleRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.net.URI.create;

@Log4j
@Service
public class RetroWallsService {

    private RetroWallRepository retroWallRepository;
    private StickyNoteStyleRepository stickyNoteStyleRepository;
    private WallStyleRepository wallStyleRepository;
    private RetroBoardRepository retroBoardRepository;

    public RetroWallsService(RetroWallRepository retroWallRepository, StickyNoteStyleRepository stickyNoteStyleRepository, WallStyleRepository wallStyleRepository, RetroBoardRepository retroBoardRepository) {
        this.retroWallRepository = retroWallRepository;
        this.stickyNoteStyleRepository = stickyNoteStyleRepository;
        this.wallStyleRepository = wallStyleRepository;
        this.retroBoardRepository = retroBoardRepository;
    }

    /**
     * Creates retro walls for the provided RetroBoardId
     * It creates in such a way that every child remembers its parent
     * and not the vise-versa. So if you have to access children
     * associated with the parent, then pass the parent id to get the children.
     * @param input
     * @return
     */
    @Transactional
    public URI createWalls(CreateRetroWallsRequest input) {

        // check if walls are already created
        // and if exists return the existing walls uri
        List<Object[]> persistedRetroWalls = retroWallRepository.findAllWallsForBoard(input.getRetroBoardId());
        if (!persistedRetroWalls.isEmpty())
            return create(format("/retro-board/walls/%s", input.getRetroBoardId()));

        // Create wall and sticky note style associated to that wall
        List<RetroWall> retroWalls = new ArrayList<>();
        List<WallStyle> wallStyles = new ArrayList<>();
        List<StickyNoteStyle> stickyNoteStyles = new ArrayList<>();

        int index = 0;
        for (CreateRetroWallRequest retroWallRequest: input.getWalls()) {
            RetroWall retroWall = CreateRetroWallRequest.createWall(retroWallRequest);

            WallStyle wallStyle = WallStyle.createWallStyle(retroWall);
            CreateStickyNoteStyleRequest stickyNoteStyleRequest = input.getWalls().get(index++).getStyle().getStickyNote();
            StickyNoteStyle stickyNoteStyle = CreateStickyNoteStyleRequest.createStickyNote(retroWall.getWallId(), wallStyle, stickyNoteStyleRequest);

            retroWalls.add(retroWall);
            wallStyles.add(wallStyle);
            stickyNoteStyles.add(stickyNoteStyle);

            log.info("Retro Wall: " + retroWall);
        }

        stickyNoteStyleRepository.saveAll(stickyNoteStyles);
        retroWallRepository.saveAll(retroWalls);
        wallStyleRepository.saveAll(wallStyles);

        return create(format("/retro-board/walls/%s", input.getRetroBoardId()));
    }

    /**
     * Invoke custom join query on RetroWall, WallStyle and StickyNoteStyle
     * to get the complete data
     * @param retroBoardId
     * @return
     */
    public RetroWallsResponse getWallsForBoard(String retroBoardId) {
        List<Object[]> response = retroWallRepository.findAllWallsForBoard(retroBoardId);
        return RetroWallsResponse.fromListObjects(response);
    }
}
