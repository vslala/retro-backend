package com.boards.core.model.entities;

import com.google.firebase.database.annotations.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String uid;
    @NotNull
    private String username;
    @NotNull
    private String displayName;
    @NotNull
    private String email;
}
