package com.boards.core.model.entities.retroboard;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "votes")
@IdClass(value = Vote.VoteId.class)
public class Vote {

    @Id
    @Column(name = "item_id")
    private String itemId;

    @Id
    @Column(name = "vote_by")
    private String voteBy;

    @Id
    @Column(name = "type")
    private String type;

    public static Vote newInstance(String itemId, String voteBy, String type) {
        Vote vote = new Vote();
        vote.setItemId(itemId);
        vote.setVoteBy(voteBy);
        vote.setType(type);
        return vote;
    }

    public enum Type {
        NOTE
    }

    @Data
    public static class VoteId implements Serializable {
        private String itemId;
        private String voteBy;
        private String type;
    }
}
