package com.boards.core.model.dto.templates;

import com.boards.core.model.dto.retroboard.StickyNoteStyleResponse;
import com.boards.core.model.dto.retroboard.WallStyleResponse;
import com.boards.core.model.entities.templates.Template;
import com.boards.core.model.entities.templates.TemplateWall;
import com.boards.core.model.entities.templates.TemplateWallNoteStyle;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static com.boards.core.configuration.AppUtil.convertToList;

@Data
public class TemplateResponse {

    private String templateId;
    private String templateTitle;
    private List<TemplateWallResponse> walls;
    private String userId;

    public static TemplateResponse createResponse(Template template, Iterable<TemplateWall> templateWalls, Iterable<TemplateWallNoteStyle> noteStyles) {

        var templateWallsResponse = convertToList(templateWalls).stream()
                .map(templateWall -> buildTemplateWallResponse(noteStyles, templateWall))
                .collect(Collectors.toList());

        var templateResponse = new TemplateResponse();
        templateResponse.setTemplateId(template.getTemplateId());
        templateResponse.setTemplateTitle(template.getTemplateTitle());
        templateResponse.setWalls(templateWallsResponse);
        templateResponse.setUserId(template.getUserId());

        return templateResponse;
    }

    private static TemplateWallResponse buildTemplateWallResponse(Iterable<TemplateWallNoteStyle> noteStyles, TemplateWall templateWall) {
        var templateWallResponse = new TemplateWallResponse();
        templateWallResponse.setWallOrder(templateWall.getWallOrder());
        templateWallResponse.setWallTitle(templateWall.getWallTitle());
        templateWallResponse.setWallStyle(convertToList(noteStyles).stream()
                .filter(noteStyle -> noteStyle.getTemplateWallId().equals(templateWall.getWallId()))
                .findFirst().map(templateWallNoteStyle -> buildWallStyleResponse(templateWallNoteStyle))
                .orElseThrow());

        return templateWallResponse;
    }

    private static WallStyleResponse buildWallStyleResponse(TemplateWallNoteStyle templateWallNoteStyle) {
        var wallStyleResponse = new WallStyleResponse();
        var stickyNoteStyleResponse = new StickyNoteStyleResponse();
        stickyNoteStyleResponse.setBackgroundColor(templateWallNoteStyle.getBackgroundColor());
        stickyNoteStyleResponse.setTextColor(templateWallNoteStyle.getTextColor());
        stickyNoteStyleResponse.setLikeBtnPosition(templateWallNoteStyle.getLikeBtnPosition());
        wallStyleResponse.setStickyNote(stickyNoteStyleResponse);
        return wallStyleResponse;
    }

    public static Templates createListTemplate(List<Template> templateList, List<TemplateWall> walls, Iterable<TemplateWallNoteStyle> noteStyles) {
        var templates = new Templates();
        templates.setTemplates(templateList.stream().map(template -> {
            // find walls for the template
            List<TemplateWall> templateWalls = walls.stream()
                    .filter(wall -> wall.getTemplateId().equals(template.getTemplateId()))
                    .collect(Collectors.toList());

            // find notes for the walls
            List<TemplateWallNoteStyle> templateWallNoteStyles = convertToList(noteStyles).stream()
                    .filter(noteStyle -> templateWalls.stream()
                            .anyMatch(templateWall -> templateWall.getWallId()
                                    .equals(noteStyle.getTemplateWallId())))
                    .collect(Collectors.toList());

            // create template response
            return createResponse(template,
                    templateWalls,
                    templateWallNoteStyles);
        })
                .collect(Collectors.toList()));

        return templates;
    }
}
