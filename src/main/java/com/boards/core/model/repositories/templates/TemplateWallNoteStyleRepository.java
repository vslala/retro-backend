package com.boards.core.model.repositories.templates;

import com.boards.core.model.entities.templates.TemplateWallNoteStyle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateWallNoteStyleRepository extends CrudRepository<TemplateWallNoteStyle, String> {

    void deleteAllByTemplateWallIdIn(List<String> wallIds);
}
