package com.boards.core.model.dto;

import com.boards.core.model.entities.RetroWall;
import com.boards.core.model.entities.StickyNoteStyle;
import com.boards.core.model.entities.WallStyle;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RetroWallsResponse {
    private List<RetroWallResponse> retroWallResponses;

    public RetroWallsResponse() {
    }

    public RetroWallsResponse(List<RetroWallResponse> retroWallResponses) {
        this.retroWallResponses = retroWallResponses;
    }

    public static RetroWallsResponse newInstance(List<RetroWallResponse> retroWallResponses) {
        return new RetroWallsResponse(retroWallResponses);
    }

    public static RetroWallsResponse fromListObjects(List<Object[]> resultSet) {
        RetroWallsResponse responses = new RetroWallsResponse();
        resultSet.forEach(objects -> {
            Arrays.asList(objects).forEach(obj -> {
                // for each object
                RetroWallResponse singleResponse = new RetroWallResponse();
                if (obj instanceof RetroWall) singleResponse.setRetroWall((RetroWall) obj);
                else if (obj instanceof WallStyle) singleResponse.setWallStyle((WallStyle) obj);
                else if (obj instanceof StickyNoteStyle) singleResponse.setStickyNoteStyle((StickyNoteStyle) obj);

                responses.add(singleResponse);
            });

        });

        return responses;
    }

    private void add(RetroWallResponse singleResponse) {
        retroWallResponses.add(singleResponse);
    }

    public boolean isEmpty() {
        return retroWallResponses.isEmpty();
    }
}
