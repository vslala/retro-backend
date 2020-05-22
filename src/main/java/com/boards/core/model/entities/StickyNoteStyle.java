package com.boards.core.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sticky_note_styles")
public class StickyNoteStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String backgroundColor;
    private String textColor;
    private String likeBtnPosition;
}
