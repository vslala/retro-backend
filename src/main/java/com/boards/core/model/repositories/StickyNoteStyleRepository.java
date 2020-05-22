package com.boards.core.model.repositories;

import com.boards.core.model.entities.StickyNoteStyle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickyNoteStyleRepository extends CrudRepository<StickyNoteStyle, Integer> {
}
