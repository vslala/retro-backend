package com.boards.core.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wall_styles")
public class WallStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer stickyNoteId;
}
