package com.boards.core.model.dto.shareditems;

import com.boards.core.model.entities.shareitems.SharedItem;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SharedItemResponse {
    private String itemId;
    private List<String> teamIds;

    public static SharedItemResponse createResponse(String itemId, List<SharedItem> sharedItems) {
        var sharedItemResponse = new SharedItemResponse();
        sharedItemResponse.setItemId(itemId);
        sharedItemResponse.setTeamIds(sharedItems.stream()
                .map(sharedItem ->
                        sharedItem.getTeamId()).collect(Collectors.toList()));
        return sharedItemResponse;
    }
}
