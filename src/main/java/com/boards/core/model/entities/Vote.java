package com.boards.core.model.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "votes")
@IdClass(value = Vote.VoteId.class)
public class Vote {

    @Id
    private String item_id;
    @Id
    private String vote_by;
    @Id
    private String type;

    @Data
    public static class VoteId implements Serializable {
        private String item_id;
        private String vote_by;
        private String type;
    }
}
