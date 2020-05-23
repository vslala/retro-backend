package com.boards.core.model.entities;

import com.boards.core.configuration.AppUtil;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wall_styles")
public class WallStyle {
    @Id
    @Column(name = "wall_style_id")
    private String wallStyleId;

    @Column(name = "wall_id")
    private String wallId;

    public static WallStyle createWallStyle(RetroWall retroWall) {
        var wallStyle = new WallStyle();
        wallStyle.setWallStyleId(AppUtil.uniqId());
        wallStyle.setWallId(retroWall.getWallId());
        return wallStyle;
    }
}
