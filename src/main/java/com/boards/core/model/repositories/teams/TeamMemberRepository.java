package com.boards.core.model.repositories.teams;

import com.boards.core.model.entities.teams.TeamMemberTeamMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMemberTeamMapping, TeamMemberTeamMapping.CompositeKey> {

    void deleteAllByTeamId(String teamId);

    List<TeamMemberTeamMapping> findAllByUid(String uid);

    List<TeamMemberTeamMapping> findAllByTeamIdIn(Collection<String> teamIds);

    List<TeamMemberTeamMapping> findAllByTeamId(String teamId);
}
