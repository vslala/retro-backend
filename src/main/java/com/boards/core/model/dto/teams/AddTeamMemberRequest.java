package com.boards.core.model.dto.teams;

import com.boards.core.model.entities.teams.TeamMemberTeamMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTeamMemberRequest {
    private String teamId;
    private String userEmail;

    public static AddTeamMemberRequest of(String teamId, String userEmail) {
        return new AddTeamMemberRequest(teamId, userEmail);
    }

    public static TeamMemberTeamMapping mapEntity(String teamId, String userId) {
        var teamMemberMapping = new TeamMemberTeamMapping();
        teamMemberMapping.setTeamId(teamId);
        teamMemberMapping.setUid(userId);
        return teamMemberMapping;
    }
}
