package com.boards.core.services;

import com.boards.core.configuration.AppUtil;
import com.boards.core.ex.ResourceNotFoundException;
import com.boards.core.model.dto.retroboard.CreateRetroWallsRequest;
import com.boards.core.model.dto.retroboard.CreateStickyNoteStyleRequest;
import com.boards.core.model.dto.retroboard.RetroWallRequest;
import com.boards.core.model.dto.retroboard.RetroWallsResponse;
import com.boards.core.model.entities.retroboard.RetroWall;
import com.boards.core.model.entities.retroboard.StickyNoteStyle;
import com.boards.core.model.entities.retroboard.WallStyle;
import com.boards.core.model.repositories.retroboard.RetroBoardRepository;
import com.boards.core.model.repositories.retroboard.RetroWallRepository;
import com.boards.core.model.repositories.retroboard.StickyNoteStyleRepository;
import com.boards.core.model.repositories.retroboard.WallStyleRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<RetroWall> persistedRetroWalls = retroWallRepository.findAllByRetroBoardId(input.getRetroBoardId());
        if (!persistedRetroWalls.isEmpty())
            return create(format("/retro-board/walls/%s", input.getRetroBoardId()));

        // Create wall and sticky note style associated to that wall
        List<RetroWall> retroWalls = new ArrayList<>();
        List<WallStyle> wallStyles = new ArrayList<>();
        List<StickyNoteStyle> stickyNoteStyles = new ArrayList<>();

        int index = 0;
        for (RetroWallRequest retroWallRequest: input.getWalls()) {
            RetroWall retroWall = RetroWallRequest.createWall(retroWallRequest);

            WallStyle wallStyle = WallStyle.createWallStyle(retroWall);
            CreateStickyNoteStyleRequest stickyNoteStyleRequest = input.getWalls().get(index++).getStyle().getStickyNote();
            StickyNoteStyle stickyNoteStyle = CreateStickyNoteStyleRequest.createStickyNote(retroWall.getWallId(), wallStyle, stickyNoteStyleRequest);

            retroWalls.add(retroWall);
            wallStyles.add(wallStyle);
            stickyNoteStyles.add(stickyNoteStyle);
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
        List<RetroWall> retroWalls = retroWallRepository.findAllByRetroBoardId(retroBoardId);
        List<WallStyle> wallStyles = AppUtil.convertToList(
                wallStyleRepository.findAllByWallIdIn(retroWalls.stream().map(RetroWall::getWallId).collect(Collectors.toList())));
        List<StickyNoteStyle> stickyNoteStyles = stickyNoteStyleRepository.findAllByWallStyleIdIn(wallStyles.stream().map(style -> style.getWallStyleId()).collect(Collectors.toList()));

        return RetroWallsResponse.createResponse(retroBoardId, retroWalls, wallStyles, stickyNoteStyles);
    }
}
