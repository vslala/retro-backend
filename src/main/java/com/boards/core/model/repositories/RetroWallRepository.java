package com.boards.core.model.repositories;

import com.boards.core.model.entities.RetroWall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetroWallRepository extends CrudRepository<RetroWall, String> {
}
