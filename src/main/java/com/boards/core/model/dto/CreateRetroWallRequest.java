package com.boards.core.model.dto;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.RetroWall;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CreateRetroWallRequest {
    private String wallId;
    private String retroBoardId;
    private String title;
    private boolean sortCards;
    private CreateWallStyleRequest style;
    private Integer wallOrder;

    public static RetroWall createWall(CreateRetroWallRequest wall) {
        RetroWall retroWall = new RetroWall();
        retroWall.setTitle(wall.getTitle());
        retroWall.setWallId(AppUtil.uniqId());
        retroWall.setRetroBoardId(wall.getRetroBoardId());
        retroWall.setSortCards(wall.isSortCards());
        retroWall.setWallStyle(AppUtil.uniqId());
        retroWall.setWallOrder(wall.getWallOrder());
        return retroWall;
    }
}
