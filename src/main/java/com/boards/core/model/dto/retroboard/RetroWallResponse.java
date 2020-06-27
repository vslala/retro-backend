package com.boards.core.model.dto.retroboard;

import com.boards.core.model.entities.retroboard.RetroWall;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RetroWallResponse {
    private String retroBoardId;
    private String wallId;
    private String title;
    private WallStyleResponse style;
    private boolean sortCards;
    private Integer wallOrder;

    public static RetroWallResponse createResponse(String retroBoardId, RetroWall retroWall, WallStyleResponse wallStyleResponse) {
        var retroWallResponse = new RetroWallResponse();
        retroWallResponse.setRetroBoardId(retroBoardId);
        retroWallResponse.setTitle(retroWall.getTitle());
        retroWallResponse.setWallId(retroWall.getWallId());
        retroWallResponse.setWallOrder(retroWall.getWallOrder());
        retroWallResponse.setStyle(wallStyleResponse);
        return retroWallResponse;
    }
}
