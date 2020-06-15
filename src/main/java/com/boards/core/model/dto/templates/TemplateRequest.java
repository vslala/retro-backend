package com.boards.core.model.dto.templates;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.dto.retroboard.CreateStickyNoteStyleRequest;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.templates.Template;
import com.boards.core.model.entities.templates.TemplateWall;
import com.boards.core.model.entities.templates.TemplateWallNoteStyle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.stream.Collectors;

import static com.boards.core.configuration.AppUtil.convertToList;

@Log4j
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TemplateRequest {

    private String templateId;
    private String templateTitle;
    private List<TemplateWallRequest> walls;

    public Template createTemplate(User loggedInUser) {
        Template template = new Template();
        template.setTemplateId(AppUtil.uniqId());
        template.setTemplateTitle(templateTitle);
        template.setUserId(loggedInUser.getUid());
        return template;
    }

    public List<TemplateWall> createTemplateWalls(String templateId) {
        return walls.stream()
                .map(templateWallRequest -> buildTemplateWall(templateId, templateWallRequest))
                .collect(Collectors.toList());
    }

    private TemplateWall buildTemplateWall(String templateId, TemplateWallRequest templateWallRequest) {
        var templateWall = new TemplateWall();
        templateWall.setTemplateId(templateId);
        templateWall.setWallId(AppUtil.uniqId());
        templateWall.setWallOrder(templateWallRequest.getWallOrder());
        templateWall.setWallTitle(templateWallRequest.getWallTitle());
        return templateWall;
    }

    /**
     * It takes the persisted walls and tries to create note styles for that.
     * Since, note styles are still with the request object,
     * it filters the wall in the request object based on its order number and
     * creates a note style with that.
     * @param persistedTemplateWalls
     * @return
     */
    public List<TemplateWallNoteStyle> createTemplateWallNoteStyle(Iterable<TemplateWall> persistedTemplateWalls) {
        return convertToList(persistedTemplateWalls).stream().map(persistedTemplateWall -> this.walls.stream()
                .filter(wallRequest -> wallRequest.getWallOrder().equals(persistedTemplateWall.getWallOrder()))
                .findFirst()
                .map(request -> buildTemplateWallNoteStyle(persistedTemplateWall, request))
                .orElseThrow()).collect(Collectors.toList());
    }

    private TemplateWallNoteStyle buildTemplateWallNoteStyle(TemplateWall persistedTemplateWall, TemplateWallRequest wallRequest) {
        log.debug("Wall Request -> " + wallRequest);
        CreateStickyNoteStyleRequest noteStyleRequest = wallRequest.getWallStyle().getStickyNote();
        TemplateWallNoteStyle noteStyle = new TemplateWallNoteStyle();
        noteStyle.setTemplateWallId(persistedTemplateWall.getWallId());
        noteStyle.setBackgroundColor(noteStyleRequest.getBackgroundColor());
        noteStyle.setTextColor(noteStyleRequest.getTextColor());
        noteStyle.setLikeBtnPosition(noteStyleRequest.getLikeBtnPosition());
        return noteStyle;
    }
}
