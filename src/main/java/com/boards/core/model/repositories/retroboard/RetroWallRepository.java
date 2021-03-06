package com.boards.core.model.repositories.retroboard;

import com.boards.core.model.entities.retroboard.RetroWall;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetroWallRepository extends CrudRepository<RetroWall, String> {

    List<RetroWall> findAllByRetroBoardId(String retroBoardId);

    @Deprecated(forRemoval = true,
            since = "since the result set returned from this query is generic, " +
                    "the consumers that utilized this method should depend on direct queries.")
    @Query("SELECT retro_wall, wall_style, sticky_note_style " +
                    "FROM RetroWall retro_wall " +
                    "LEFT JOIN WallStyle wall_style ON retro_wall.wallId=wall_style.wallId " +
                    "LEFT JOIN StickyNoteStyle  sticky_note_style ON sticky_note_style.wallStyleId=wall_style.wallStyleId " +
                    "WHERE retro_wall.retroBoardId = :retroBoardId " +
            "ORDER BY retro_wall.wallOrder ASC ")
    List<Object[]> findAllWallsForBoard(@Param("retroBoardId") String retroBoardId);

    void deleteByRetroBoardId(String retroBoardId);
}
