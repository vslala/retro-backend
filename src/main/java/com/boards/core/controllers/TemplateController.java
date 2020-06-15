package com.boards.core.controllers;

import com.boards.core.model.dto.templates.TemplateRequest;
import com.boards.core.model.dto.templates.TemplateResponse;
import com.boards.core.model.dto.templates.Templates;
import com.boards.core.services.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.boards.core.configuration.AppConfig.EXPOSE_LOCATION;
import static com.boards.core.configuration.AppUtil.getLoggedInUser;
import static org.springframework.http.ResponseEntity.*;

@CrossOrigin(exposedHeaders = EXPOSE_LOCATION)
@RestController
@RequestMapping("/templates")
public class TemplateController {

    private TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createTemplate(@RequestBody TemplateRequest templateRequest) {
        URI uri = templateService.createTemplate(getLoggedInUser(), templateRequest);
        return created(uri).build();
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateResponse> getTemplate(@PathVariable String templateId) {
        TemplateResponse templateResponse = templateService.getTemplate(getLoggedInUser(), templateId);
        return ok(templateResponse);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<HttpStatus> deleteTemplate(@PathVariable String templateId) {
        templateService.deleteTemplate(getLoggedInUser(), templateId);
        return noContent().build();
    }

    @GetMapping
    public ResponseEntity<Templates> getMyTemplates() {
        Templates templates = templateService.getTemplates(getLoggedInUser());
        return ok(templates);
    }
}
