package com.boards.core.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "notes")
public class Note {
    @Id
    private String noteId;
    private String noteText;
    private String retroBoardId;
    private String wallId;
    private String createdBy;

}
