package com.boards.core.model.dto.shareditems;

import com.boards.core.model.entities.shareitems.SharedItem;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SharedItemRequest {
    private String itemId;
    private List<String> teamIds;

    public List<SharedItem> createSharedItems() {
        return teamIds.stream().map(teamId ->
                SharedItem.newInstance(itemId, teamId)).collect(Collectors.toList());
    }
}
