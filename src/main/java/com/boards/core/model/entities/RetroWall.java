package com.boards.core.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "retro_walls")
public class RetroWall {
    @Id
    @Column(name = "wall_id")
    private String wallId;

    @Column(name = "retro_board_id")
    private String retroBoardId;

    @Column(name = "title")
    private String title;

    @Column(name = "wall_style")
    private String wallStyle;

    @Column(name = "sort_cards")
    private boolean sortCards;

}
