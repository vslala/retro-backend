package com.boards.core.model.entities.templates;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "template_wall_sticky_note_styles")
public class TemplateWallNoteStyle {

    @Id
    @Column(name = "template_wall_id")
    private String templateWallId;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "text_color")
    private String textColor;

    @Column(name = "like_btn_position")
    private String likeBtnPosition;
}
