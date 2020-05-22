package com.boards.core.model.repositories;

import com.boards.core.model.RetroBoard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetroBoardRepository extends CrudRepository<RetroBoard, String> {
    List<RetroBoard> findAll();
}
