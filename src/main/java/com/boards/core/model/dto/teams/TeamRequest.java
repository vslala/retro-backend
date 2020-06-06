package com.boards.core.model.dto.teams;

import com.boards.core.configuration.AppUtil;
import com.boards.core.model.dto.retroboard.UserRequest;
import com.boards.core.model.entities.teams.Team;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.annotations.NotNull;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamRequest {
    String teamId;
    @NotNull
    private String teamName;
    private List<UserRequest> teamMembers;
    @NotNull
    private String createdBy;

    public Team createNewTeam() {
        var team = new Team();
        team.setTeamId(AppUtil.uniqId());
        team.setCreatedBy(createdBy);
        team.setTeamName(teamName);
        return team;
    }
}
