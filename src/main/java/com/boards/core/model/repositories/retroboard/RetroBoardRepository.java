package com.boards.core.model.repositories.retroboard;

import com.boards.core.model.entities.retroboard.RetroBoard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetroBoardRepository extends CrudRepository<RetroBoard, String> {
    List<RetroBoard> findAll();

    List<RetroBoard> findAllByUserId(String uid);
}
