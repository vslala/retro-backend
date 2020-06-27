package com.boards.core.model.repositories.retroboard;

import com.boards.core.model.entities.retroboard.StickyNoteStyle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StickyNoteStyleRepository extends CrudRepository<StickyNoteStyle, String> {

    List<StickyNoteStyle> findAllByWallStyleId(String wallId);

    List<StickyNoteStyle> findAllByWallStyleIdIn(List<String> wallStyleIds);
}
