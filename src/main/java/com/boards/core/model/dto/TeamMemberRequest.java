package com.boards.core.model.dto;

import com.boards.core.model.dto.teams.TeamRequest;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.model.entities.teams.Team;
import com.boards.core.model.entities.teams.TeamMemberTeamMapping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamMemberRequest {
    @NotNull
    private TeamRequest team;

    @NotNull
    @Email
    private User teamMember;

    public static TeamMemberTeamMapping createTeamMember(TeamMemberRequest teamMemberRequest, User user) {
        var teamMember = new TeamMemberTeamMapping();
        teamMember.setTeamId(teamMemberRequest.getTeam().getTeamId());
        teamMember.setUid(user.getUid());
        return teamMember;
    }

    public static TeamMemberTeamMapping addTeamMember(Team persistedTeam, User user) {
        var teamMember = new TeamMemberTeamMapping();
        teamMember.setTeamId(persistedTeam.getTeamId());
        teamMember.setUid(user.getUid());
        return teamMember;
    }
}
