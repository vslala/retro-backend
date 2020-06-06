package com.boards.core.model.repositories.teams;

import com.boards.core.model.entities.teams.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends CrudRepository<Team, String> {
    List<Team> findAllByCreatedBy(String uid);
    List<Team> findAllByTeamIdIn(List<String> teamIds);
}
