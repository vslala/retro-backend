package com.boards.core.model;

import com.google.firebase.database.annotations.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Base64;

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

    public static RetroBoard newInstance(String name, int maxLikes) {
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setName(name);
        retroBoard.setMaxLikes(maxLikes);
        return retroBoard;
    }

    public void generateUniqId() {
        String currentTime = String.valueOf(System.currentTimeMillis());
        this.id = Base64.getEncoder().encodeToString(currentTime.getBytes());
    }
}
