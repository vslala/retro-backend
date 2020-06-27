package com.boards.core.model.repositories.retroboard;

import com.boards.core.model.entities.retroboard.WallStyle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WallStyleRepository extends CrudRepository<WallStyle, String> {

    List<WallStyle> findAllByWallIdIn(List<String> wallIds);
}
