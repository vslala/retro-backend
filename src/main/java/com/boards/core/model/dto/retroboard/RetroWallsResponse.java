package com.boards.core.model.dto.retroboard;

import com.boards.core.model.entities.retroboard.RetroWall;
import com.boards.core.model.entities.retroboard.StickyNoteStyle;
import com.boards.core.model.entities.retroboard.WallStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Log4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetroWallsResponse {
    private String retroBoardId;
    private List<RetroWallResponse> walls;

    public RetroWallsResponse() {
    }

    public RetroWallsResponse(List<RetroWallResponse> walls) {
        this.walls = walls;
    }

    public static RetroWallsResponse newInstance(List<RetroWallResponse> retroWallResponses) {
        return new RetroWallsResponse(retroWallResponses);
    }

    public static RetroWallsResponse fromListObjects(List<Object[]> resultSet) {
        log.debug("Generating response for walls...");
        RetroWallsResponse responses = new RetroWallsResponse();
        for (Object[] objects : resultSet) {
            var retroWall = (RetroWall) objects[0];
            var wallStyle = (WallStyle) objects[1];
            var stickyNoteStyle = (StickyNoteStyle) objects[2];

            var singleStickyNoteStyle = new StickyNoteStyleResponse();
            if (! Objects.isNull(stickyNoteStyle)) {
                singleStickyNoteStyle.setBackgroundColor(stickyNoteStyle.getBackgroundColor());
                singleStickyNoteStyle.setLikeBtnPosition(stickyNoteStyle.getLikeBtnPosition());
                singleStickyNoteStyle.setTextColor(stickyNoteStyle.getTextColor());
            }


            var singleWallStyleResponse = new WallStyleResponse();
            singleWallStyleResponse.setStickyNote(singleStickyNoteStyle);

            var singleResponse = new RetroWallResponse();
            singleResponse.setWallId(retroWall.getWallId());
            singleResponse.setTitle(retroWall.getTitle());
            singleResponse.setSortCards(retroWall.isSortCards());
            singleResponse.setRetroBoardId(retroWall.getRetroBoardId());
            singleResponse.setStyle(singleWallStyleResponse);


            responses.add(singleResponse);

        }

        return responses;
    }

    private void add(RetroWallResponse singleResponse) {
        if (Objects.isNull(this.walls))
            this.walls = new ArrayList<>();
        this.walls.add(singleResponse);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return Objects.isNull(walls) ? true : walls.isEmpty();
    }
}
