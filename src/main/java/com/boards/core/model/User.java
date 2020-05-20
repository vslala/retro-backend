package com.boards.core.model;

import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
