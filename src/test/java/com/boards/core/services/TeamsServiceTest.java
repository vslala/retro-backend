package com.boards.core.services;

import com.boards.core.fixtures.ServiceFixture;
import com.boards.core.model.dto.TeamMemberRequest;
import com.boards.core.model.dto.teams.AddTeamMemberRequest;
import com.boards.core.model.dto.teams.TeamListResponse;
import com.boards.core.model.dto.teams.TeamMemberListResponse;
import com.boards.core.model.dto.teams.TeamResponse;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.teams.Team;
import com.boards.core.model.entities.teams.TeamMemberTeamMapping;
import com.boards.core.model.repositories.retroboard.UserRepository;
import com.boards.core.model.repositories.teams.TeamMemberRepository;
import com.boards.core.model.repositories.teams.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class TeamsServiceTest {

    private TeamsService teamsService;
    private TeamRepository teamRepository = Mockito.mock(TeamRepository.class);
    private TeamMemberRepository teamMemberRepository = Mockito.mock(TeamMemberRepository.class);
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private User loggedInUser;

    @BeforeEach
    void setup() {
        loggedInUser = new User();
        loggedInUser.setUid("random_id");
        loggedInUser.setEmail("operator@bma.com");

        teamsService = new TeamsService(teamRepository, teamMemberRepository, userRepository);
    }

    @Test
    void givenLoggedInUser_itShouldFetchAllTheTeamsAssociatedWithThatUser() {
        // Given
        Team team = ServiceFixture.buildTeam("TeamID", "Team Name", loggedInUser.getUid());

        // When
        when(teamRepository.findAllByCreatedBy(loggedInUser.getUid())).thenReturn(List.of(team));
        TeamListResponse teamResponse = teamsService.getMyTeams(loggedInUser);

        // Then
        assertNotNull(teamResponse);
        assertEquals(1, teamResponse.getTeams().size());
        assertEquals("TeamID", teamResponse.getTeams().get(0).getTeamId());
    }

    @Test
    void givenTeamId_itShouldFetchAllItsAssociatedTeamMembers() {
        // Given
        var team = ServiceFixture.buildTeam("TeamID", "TeamName", loggedInUser.getUid());

        // When
        when(teamMemberRepository.findAllByTeamId(team.getTeamId())).thenReturn(List.of(ServiceFixture.buildTeamMemberTeamMapping(team, loggedInUser)));
        when(userRepository.findAllByUidIn(any())).thenReturn(List.of(loggedInUser));
        TeamMemberListResponse teamMembers = teamsService.getTeamMembers(team.getTeamId());

        // Then
        assertNotNull(teamMembers);
        assertEquals("TeamID", teamMembers.getTeamId());
        assertEquals(1, teamMembers.getTeamMembers().size());
    }

    @Test
    void givenTeamIdAndTeamMemberEmail_itShouldAddNewTeamMemberToTheTeam() {
        // Given
        var team = ServiceFixture.buildTeam("team_id", "team_name", loggedInUser.getUid());
        var userEmail = loggedInUser.getEmail();

        // When
        var teamMemberMapping = new TeamMemberTeamMapping();
        teamMemberMapping.setTeamId(team.getTeamId());
        teamMemberMapping.setUid(loggedInUser.getUid());
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(loggedInUser));
        when(teamMemberRepository.save(any())).thenReturn(teamMemberMapping);
        URI uri = teamsService.addTeamMember(AddTeamMemberRequest.of(team.getTeamId(), userEmail));

        // Then
        assertEquals(String.format("/teams/%s/members", team.getTeamId()), uri.toString());
    }
}