package com.boards.core.model.entities.teams;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "team_members")
@IdClass(value = TeamMember.CompositeKey.class)
public class TeamMember {

    @Id
    @Column(name = "team_id")
    private String teamId;

    @Id
    @Column(name = "team_member_uid")
    private String uid;

    @Data
    public static class CompositeKey implements Serializable {
        private String teamId;
        private String uid;
    }
}
