package com.boards.core.model.entities;

import com.google.firebase.database.annotations.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
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
    private String blur;

    @Column(name = "max_likes")
    @NotNull
    private Integer maxLikes;

    @Column(name = "user_id")
    @NotNull
    private String userId;

}
