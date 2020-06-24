package com.boards.core.services;

import com.boards.core.ex.ResourceNotFoundException;
import com.boards.core.ex.UnauthorizedUser;
import com.boards.core.model.dto.templates.TemplateRequest;
import com.boards.core.model.dto.templates.TemplateResponse;
import com.boards.core.model.dto.templates.Templates;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.templates.Template;
import com.boards.core.model.entities.templates.TemplateWall;
import com.boards.core.model.entities.templates.TemplateWallNoteStyle;
import com.boards.core.model.repositories.templates.TemplateRepository;
import com.boards.core.model.repositories.templates.TemplateWallNoteStyleRepository;
import com.boards.core.model.repositories.templates.TemplateWallRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.boards.core.configuration.AppUtil.convertToList;

@Log4j
@Service
public class TemplateService {

    private TemplateRepository templateRepository;
    private TemplateWallRepository templateWallRepository;
    private TemplateWallNoteStyleRepository templateWallNoteStyleRepository;

    public TemplateService(TemplateRepository templateRepository, TemplateWallRepository templateWallRepository, TemplateWallNoteStyleRepository templateWallNoteStyleRepository) {
        this.templateRepository = templateRepository;
        this.templateWallRepository = templateWallRepository;
        this.templateWallNoteStyleRepository = templateWallNoteStyleRepository;
    }

    @Transactional
    public URI createTemplate(User loggedInUser, TemplateRequest templateRequest) {
        Template persistedTemplate = templateRepository.save(templateRequest.createTemplate(loggedInUser));
        Iterable<TemplateWall> walls = templateWallRepository.saveAll(templateRequest.createTemplateWalls(persistedTemplate.getTemplateId()));
        Iterable<TemplateWallNoteStyle> noteStyles = templateWallNoteStyleRepository.saveAll(templateRequest.createTemplateWallNoteStyle(walls));

        return URI.create(String.format("/templates/%s", persistedTemplate.getTemplateId()));
    }

    public TemplateResponse getTemplate(User loggedInUser, String templateId) {

        return templateRepository.findById(templateId)
                .map(template -> {
                    if (!loggedInUser.getUid().equals(template.getUserId()))
                        throw new UnauthorizedUser("User is not authorized to make this request!!!");

                    Iterable<TemplateWall> templateWalls = templateWallRepository.findAllByTemplateId(templateId);
                    List<String> wallIds = convertToList(templateWalls).stream().map(templateWall -> templateWall.getWallId()).collect(Collectors.toList());

                    return TemplateResponse.createResponse(template, templateWalls, templateWallNoteStyleRepository.findAllById(wallIds));
                }).orElseThrow(() -> new ResourceNotFoundException("Template not found!!!"));
    }

    @Transactional
    public void deleteTemplate(User loggedInUser, String templateId) {
        templateRepository.findById(templateId)
                .ifPresent(template -> {
                    if (!template.getUserId().equals(loggedInUser.getUid()))
                        throw new UnauthorizedUser("User is not authorized to make this request!!!");

                    templateWallNoteStyleRepository.deleteAllByTemplateWallIdIn(
                            convertToList(templateWallRepository.findAllByTemplateId(templateId))
                                .stream().map(TemplateWall::getWallId).collect(Collectors.toList())
                    );
                    templateWallRepository.deleteByTemplateId(templateId);
                    templateRepository.deleteById(templateId);
                });
    }

    public Templates getTemplates(User loggedInUser) {
        List<Template> templateList = templateRepository.findAllByUserId(loggedInUser.getUid());
        List<TemplateWall> walls = templateWallRepository.findAllByTemplateIdIn(
                templateList.stream()
                        .map(template -> template.getTemplateId())
                        .collect(Collectors.toList()));
        List<String> wallIds = convertToList(walls.stream().map(templateWall -> templateWall.getWallId()).collect(Collectors.toList()));
        List<TemplateWallNoteStyle> noteStyles = convertToList(templateWallNoteStyleRepository.findAllById(wallIds));

        return TemplateResponse.createListTemplate(templateList, walls, noteStyles);
    }
}
