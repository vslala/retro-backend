package com.boards.core.services;

import com.boards.core.ex.ResourceNotFoundException;
import com.boards.core.ex.UnauthorizedUser;
import com.boards.core.model.dto.TeamMemberRequest;
import com.boards.core.model.dto.teams.TeamMemberResponse;
import com.boards.core.model.dto.teams.TeamRequest;
import com.boards.core.model.dto.teams.TeamResponse;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.teams.Team;
import com.boards.core.model.entities.teams.TeamMember;
import com.boards.core.model.repositories.retroboard.UserRepository;
import com.boards.core.model.repositories.teams.TeamMemberRepository;
import com.boards.core.model.repositories.teams.TeamRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
            List<TeamMember> teamMembersId = teamMemberRepository.findAllByTeamId(teamId);
            List<String> userIds = teamMembersId.stream()
                    .map(teamMember -> teamMember.getUid())
                    .collect(Collectors.toList());
            Iterable<User> teamMembers = userRepository.findAllById(userIds);
            return Optional.of(TeamResponse.createResponse(team.get(), teamMembers));
        }
        return Optional.empty();
    }

    public URI addTeamMember(TeamMemberRequest teamMemberRequest) {
        Optional<User> user = userRepository.findById(teamMemberRequest.getTeamMember().getUid());
        if (user.isPresent())
            teamMemberRepository.save(TeamMemberRequest.createTeamMember(teamMemberRequest,user.get()));
        // TODO: Return team members for the team
        return create(format("/teams/%s/members", teamMemberRequest.getTeam().getTeamId()));
    }

    public TeamMemberResponse getTeamMembers(String teamId) {
        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeamId(teamId);
        Iterable<User> users = userRepository.findAllById(
            teamMembers.stream().map(teamMember -> teamMember.getUid()).collect(Collectors.toList())
        );

        return TeamMemberResponse.createResponse(users);
    }

    @Transactional
    public void deleteTeam(String teamId, User loggedInUser) {
        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isPresent() && loggedInUser.getUid().equals(team.get().getCreatedBy())) {
            teamMemberRepository.deleteAllByTeamId(teamId);
            teamRepository.deleteById(teamId);
        }

        throw new UnauthorizedUser("<TeamsService.deleteTeam()>");
    }

    public Set<TeamResponse> getMyTeams(User loggedInUser) {
        // get all my team Ids from the team member mapping table
        List<TeamMember> userTeamMapping = teamMemberRepository.findAllByUid(loggedInUser.getUid());

        // get all user teams by team ids
        List<Team> teams = teamRepository.findAllByTeamIdIn(
                userTeamMapping.stream().map(teamMember -> teamMember.getTeamId()).collect(Collectors.toList())
        );

        // find all the members in the teams created by the user
        List<TeamMember> teamMembersMapping = teamMemberRepository.findAllByTeamIdIn(
                teams.stream().map(team -> team.getTeamId()).collect(Collectors.toList())
        );

        // find all the users who are part of the anyone of the user created team
        List<User> teamMembers = userRepository.findAllByUidIn(
                teamMembersMapping.stream().map(teamMember -> teamMember.getUid()).collect(Collectors.toList())
        );

        return TeamResponse.createTeamsResponse(teams, teamMembersMapping, teamMembers);
    }

    public void removeTeamMember(User loggedInUser, String teamId, String uid) {
        Optional<Team> persistedTeam = teamRepository.findById(teamId);
        persistedTeam.map(team -> {
            if (! team.getCreatedBy().equals(loggedInUser.getUid())) throw new UnauthorizedUser("User not authorized to remote the team member.");

            var compositeKey = new TeamMember.CompositeKey();
            compositeKey.setTeamId(team.getTeamId());
            compositeKey.setUid(uid);
            teamMemberRepository.deleteById(compositeKey);

            return 1;
        }).orElseThrow(() -> new ResourceNotFoundException("Member with the given id not found!"));
    }
}
