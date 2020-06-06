package com.boards.core.model.entities.shareitems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "shared_items")
@IdClass(SharedItem.CompositeKey.class)
@NoArgsConstructor
@AllArgsConstructor
public class SharedItem {

    @Id
    @Column(name = "item_id")
    private String itemId;

    @Id
    @Column(name = "team_id")
    private String teamId;

    public static SharedItem newInstance(String itemId, String teamId) {
        return new SharedItem(itemId, teamId);
    }

    @Data
    public static class CompositeKey implements Serializable {
        private String itemId;
        private String teamId;
    }
}
