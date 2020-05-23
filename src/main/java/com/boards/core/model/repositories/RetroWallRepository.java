package com.boards.core.model.repositories;

import com.boards.core.model.entities.RetroWall;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetroWallRepository extends CrudRepository<RetroWall, String> {

//    List<RetroWall> saveAll(List<RetroWall> retroWalls);

    List<RetroWall> findAllByRetroBoardId(String retroBoardId);

    @Query("SELECT retro_wall, wall_style, sticky_note_style " +
                    "FROM RetroWall retro_wall " +
                    "LEFT JOIN WallStyle wall_style ON retro_wall.wallId=wall_style.wallId " +
                    "LEFT JOIN StickyNoteStyle  sticky_note_style ON sticky_note_style.wallStyleId=wall_style.wallStyleId " +
                    "WHERE retro_wall.retroBoardId = :retroBoardId")
    List<Object[]> findAllWallsForBoard(@Param("retroBoardId") String retroBoardId);
}
