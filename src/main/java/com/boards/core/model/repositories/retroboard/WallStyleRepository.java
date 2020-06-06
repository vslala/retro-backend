package com.boards.core.model.repositories.retroboard;

import com.boards.core.model.entities.retroboard.WallStyle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallStyleRepository extends CrudRepository<WallStyle, Integer> {

}
