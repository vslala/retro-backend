package com.boards.core.model.entities.retroboard;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_boards")
@IdClass(UserBoard.UserBoardCompositeId.class)
public class UserBoard {

    @Id
    @Column(name = "uid")
    private String uid;

    @Id
    @Column(name = "board_id")
    private String boardId;

    @Data
    public static class UserBoardCompositeId implements Serializable {
        private String uid;
        private String boardId;

        public UserBoardCompositeId(String uid, String boardId) {
            this.uid = uid;
            this.boardId = boardId;
        }
    }
}
