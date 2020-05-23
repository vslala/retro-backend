package com.boards.core.model.repositories;

import com.boards.core.model.entities.StickyNoteStyle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StickyNoteStyleRepository extends CrudRepository<StickyNoteStyle, String> {

}
