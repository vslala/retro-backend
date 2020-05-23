package com.boards.core.model.repositories;

import com.boards.core.model.entities.NoteStyle;
import org.springframework.data.repository.CrudRepository;

public interface NoteStyleRepository extends CrudRepository<NoteStyle, String> {
}
