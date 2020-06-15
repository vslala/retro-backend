package com.boards.core.model.repositories.templates;

import com.boards.core.model.entities.templates.TemplateWall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateWallRepository extends CrudRepository<TemplateWall, String> {
    List<TemplateWall> findAllByTemplateIdIn(List<String> templateId);

    void deleteByTemplateId(String templateId);

    Iterable<TemplateWall> findAllByTemplateId(String templateId);
}
