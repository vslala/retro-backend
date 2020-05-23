package com.boards.core.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "note_styles")
public class NoteStyle {
    @Id
    @Column(name = "note_style_id")
    private String noteStyleId;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "text_color")
    private String textColor;

    @Column(name = "like_btn_position")
    private String likeBtnPosition;
}
