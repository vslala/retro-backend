package com.boards.core.model.repositories.templates;

import com.boards.core.model.entities.templates.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends CrudRepository<Template, String> {
    List<Template> findAllByUserId(String uid);
}
