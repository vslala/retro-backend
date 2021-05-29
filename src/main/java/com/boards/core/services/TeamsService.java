package com.boards.core.services;

import com.boards.core.ex.ResourceNotFoundException;
import com.boards.core.ex.UnauthorizedUser;
import com.boards.core.model.dto.TeamMemberRequest;
import com.boards.core.model.dto.teams.*;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.teams.Team;
import com.boards.core.model.entities.teams.TeamMemberTeamMapping;
import com.boards.core.model.repositories.retroboard.UserRepository;
import com.boards.core.model.repositories.teams.TeamMemberRepository;
import com.boards.core.model.repositories.teams.TeamRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.net.URI.create;

@Log4j
@Service
public class TeamsService {

    private TeamRepository teamRepository;
    private TeamMemberRepository teamMemberRepository;
    private UserRepository userRepository;

    public TeamsService(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public URI createNewTeam(User loggedInUser, TeamRequest teamRequest) {
        Team persistedTeam = teamRepository.save(teamRequest.createNewTeam());
        teamMemberRepository.save(TeamMemberRequest.addTeamMember(persistedTeam, loggedInUser));
        return create(format("/teams/%s", persistedTeam.getTeamId()));
    }

    public Optional<TeamResponse> getTeam(String teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isPresent()) {
            List<TeamMemberTeamMapping> teamMembersIdTeamMapping = teamMemberRepository.findAllByTeamId(teamId);
            List<String> userIds = teamMembersIdTeamMapping.stream()
                    .map(teamMember -> teamMember.getUid())
                    .collect(Collectors.toList());
            Iterable<User> teamMembers = userRepository.findAllById(userIds);
            return Optional.of(TeamResponse.createResponse(team.get(), teamMembers));
        }
        return Optional.empty();
    }

    public URI addTeamMember(AddTeamMemberRequest addTeamMemberRequest) {
        Optional<User> user = userRepository.findByEmail(addTeamMemberRequest.getUserEmail());

        user.ifPresent(value -> teamMemberRepository.save(AddTeamMemberRequest.mapEntity(addTeamMemberRequest.getTeamId(), value.getUid())));

        return create(format("/teams/%s/members", addTeamMemberRequest.getTeamId()));
    }

    public TeamMemberListResponse getTeamMembers(String teamId) {
        List<TeamMemberTeamMapping> teamMemberTeamMappings = teamMemberRepository.findAllByTeamId(teamId);
        List<User> users = userRepository.findAllByUidIn(
                teamMemberTeamMappings.stream()
                        .map(TeamMemberTeamMapping::getUid)
                        .collect(Collectors.toList()));

        return TeamMemberListResponse.of(teamId, users);
    }

    @Transactional
    public void deleteTeam(String teamId, User loggedInUser) {
        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isPresent() && loggedInUser.getUid().equals(team.get().getCreatedBy())) {
            teamMemberRepository.deleteAllByTeamId(teamId);
            teamRepository.deleteById(teamId);
            return;
        }

        throw new UnauthorizedUser("<TeamsService.deleteTeam()>. Team ID: " + teamId + ", Is Team Present with the given ID: " + team.isPresent());
    }

    public TeamListResponse getMyTeams(User loggedInUser) {
        List<Team> teams = teamRepository.findAllByCreatedBy(loggedInUser.getUid());
        return TeamListResponse.of(teams);
    }

    @Transactional
    public void removeTeamMember(User loggedInUser, String teamId, String uid) {
        Optional<Team> persistedTeam = teamRepository.findById(teamId);
        persistedTeam.map(team -> {
            if (! team.getCreatedBy().equals(loggedInUser.getUid())) throw new UnauthorizedUser("User not authorized to remove the team member.");

            var compositeKey = new TeamMemberTeamMapping.CompositeKey();
            compositeKey.setTeamId(team.getTeamId());
            compositeKey.setUid(uid);
            teamMemberRepository.deleteById(compositeKey);

            return 1;
        }).orElseThrow(() -> new ResourceNotFoundException("Member with the given id not found!"));
    }
}
