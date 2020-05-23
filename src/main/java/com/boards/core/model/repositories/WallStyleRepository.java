package com.boards.core.model.repositories;

import com.boards.core.model.entities.WallStyle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WallStyleRepository extends CrudRepository<WallStyle, Integer> {

}
