package com.boards.core.model.dto.teams;

import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.teams.Team;
import com.boards.core.model.entities.teams.TeamMember;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j
@Data
@Builder
public class TeamResponse {
    private String teamId;
    private String teamName;
    private List<User> teamMembers;
    private String createdBy;

    public static TeamResponse createResponse(Team team, Iterable<User> teamMembersItr) {
        List<User> teamMembers = StreamSupport.stream(teamMembersItr.spliterator(), false).collect(Collectors.toList());

        return new TeamResponseBuilder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .teamMembers(teamMembers)
                .createdBy(team.getCreatedBy()).build();
    }

    /**
     * Maps the team members with their respective teams
     * using the team member mapping list
     * @param teams
     * @param teamMembersMapping
     * @param teamMembers
     * @return
     */
    public static Set<TeamResponse> createTeamsResponse(List<Team> teams, List<TeamMember> teamMembersMapping, List<User> teamMembers) {
        Set<TeamResponse> teamResponses = new HashSet<>();
        teams.forEach(team -> {
            List<User> members = teamMembersMapping.parallelStream()
                    .filter(teamMemberMapping ->
                            teamMemberMapping.getTeamId().equals(team.getTeamId()))
                    .map(memberMapping ->
                            teamMembers.stream()
                                    .filter(teamMember -> teamMember.getUid().equals(memberMapping.getUid()))
                                    .findFirst().get()) // sure to find a user
                    .collect(Collectors.toList());
            teamResponses.add(TeamResponse.createResponse(team, members));
        });

        return teamResponses;
    }

}
