package com.boards.core.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "retro_walls")
public class RetroWall {
    private String retroBoardId;
    @Id
    private String wallId;
    private String title;
    private Integer wallStyle;
    private boolean sortCards;
}
