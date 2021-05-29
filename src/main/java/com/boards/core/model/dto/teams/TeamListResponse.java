package com.boards.core.model.dto.teams;

import com.boards.core.model.entities.teams.Team;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class TeamListResponse {
    private List<Team> teams;

    public static TeamListResponse of(List<Team> teams) {
        var teamList = new TeamListResponse();
        teamList.setTeams(teams);
        return teamList;
    }
}
