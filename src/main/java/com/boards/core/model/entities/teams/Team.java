package com.boards.core.model.entities.teams;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "teams")
public class Team {

    @Id
    private String teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "created_by")
    private String createdBy;
}
