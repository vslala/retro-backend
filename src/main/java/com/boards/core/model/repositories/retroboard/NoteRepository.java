package com.boards.core.model.repositories.retroboard;

import com.boards.core.model.entities.retroboard.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, String> {
    List<Note> findAllByWallId(String wallId);

    List<Note> findAllByRetroBoardId(String retroBoardId);

    void deleteAllByWallIdIn(List<String> collect);
}
