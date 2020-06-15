package com.boards.core.model.entities.templates;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "templates")
public class Template {

    @Id
    @Column(name = "template_id")
    private String templateId;

    @Column(name = "template_title")
    private String templateTitle;

    @Column(name = "user_id")
    private String userId;
}
