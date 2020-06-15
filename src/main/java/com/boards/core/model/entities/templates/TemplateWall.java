package com.boards.core.model.entities.templates;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "template_walls")
public class TemplateWall {

    @Id
    @Column(name = "wall_id")
    private String wallId;

    @Column(name = "template_id")
    private String templateId;

    @Column(name = "wall_title")
    private String wallTitle;

    @Column(name = "wall_order")
    private Integer wallOrder;
}
