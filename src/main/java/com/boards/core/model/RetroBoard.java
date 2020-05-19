package com.boards.core.model;

import com.google.firebase.database.annotations.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "retro_boards")
public class RetroBoard {
    @Id
    private String id;
    @NotNull
    private String name;
    private String blur;
    @NotNull
    private Integer maxLikes;
}
