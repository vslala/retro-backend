package com.boards.core.model.dto;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.entities.RetroBoard;
import com.boards.core.model.entities.User;
import com.google.firebase.database.annotations.NotNull;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
public class RetroBoardRequest {
    @NotNull
    private String id;

    @NotNull
    private String blur;

    @NotNull
    private String userId;

    @NotNull
    private String name;

    @NotNull
    private Integer maxLikes;

    public RetroBoard createRetroBoard() {
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setId(AppUtil.uniqId());
        retroBoard.setName(name);
        retroBoard.setMaxLikes(maxLikes);
        retroBoard.setBlur(blur);
        retroBoard.setUserId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        return retroBoard;
    }

    public RetroBoard updateRetroBoard() {
        RetroBoard retroBoard = createRetroBoard();
        retroBoard.setId(id);
        return retroBoard;
    }
}
