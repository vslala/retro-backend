package com.boards.core.model.dto.teams;

import com.boards.core.model.entities.retroboard.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Log4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberListResponse {
    private String teamId;
    private List<User> teamMembers;

    public static TeamMemberListResponse of(String teamId, List<User> teamMembers) {
        return new TeamMemberListResponse(teamId, teamMembers);
    }
}
