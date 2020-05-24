package com.boards.core.model.entities;

import com.boards.core.configuration.AppConfig;
import com.boards.core.configuration.AppUtil;
import com.google.firebase.database.annotations.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "retro_boards")
public class RetroBoard {
    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "blur")
    private String blur = "off";

    @Column(name = "max_likes")
    @NotNull
    private Integer maxLikes;

    @Column(name = "user_id")
    @NotNull
    private String userId = "someuserid";

    public static RetroBoard newInstance(String name, int maxLikes) {
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setId(AppUtil.uniqId());
        retroBoard.setName(name);
        retroBoard.setMaxLikes(maxLikes);
        retroBoard.setUserId(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        return retroBoard;
    }

    public void generateUniqId() {
        this.id = AppUtil.uniqId();
    }
}
