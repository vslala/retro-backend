package com.boards.core.model.dto;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.RetroWall;
import lombok.Data;

@Data
public class CreateRetroWallRequest {
    private String retroBoardId;
    private String title;
    private boolean sortCards;
    private CreateWallStyleRequest style;

    public RetroWall createWall() {
        RetroWall retroWall = new RetroWall();
        retroWall.setTitle(title);
        retroWall.setWallId(AppUtil.uniqId());
        retroWall.setRetroBoardId(retroBoardId);
        retroWall.setSortCards(sortCards);
        retroWall.setWallStyle(AppUtil.uniqId());
        return retroWall;
    }
}
