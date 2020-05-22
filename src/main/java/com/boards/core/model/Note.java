package com.boards.core.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @Column(name = "note_id")
    private String noteId;

    @Column(name = "note_text")
    private String noteText;

    @Column(name = "board_id")
    private String retroBoardId;

    @Column(name = "wall_id")
    private String wallId;

    @Column(name = "created_by")
    private String createdBy;

}
