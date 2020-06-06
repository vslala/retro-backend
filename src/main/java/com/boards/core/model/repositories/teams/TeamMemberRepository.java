package com.boards.core.model.repositories.teams;

import com.boards.core.model.entities.teams.TeamMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember, TeamMember.CompositeKey> {

    void deleteAllByTeamId(String teamId);

    List<TeamMember> findAllByUid(String uid);

    List<TeamMember> findAllByTeamIdIn(Collection<String> teamIds);

    List<TeamMember> findAllByTeamId(String teamId);
}
