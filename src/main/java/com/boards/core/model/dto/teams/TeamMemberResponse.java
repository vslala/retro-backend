package com.boards.core.model.dto.teams;

import com.boards.core.model.entities.retroboard.User;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

@Data
public class TeamMemberResponse {
    public List<User> teamMembers;

    public static TeamMemberResponse createResponse(Iterable<User> teamMembers) {
        TeamMemberResponse teamMemberResponse = new TeamMemberResponse();
        teamMemberResponse.setTeamMembers(isNull(teamMembers) ? Collections.emptyList() :
                StreamSupport
                        .stream(teamMembers.spliterator(), false)
                        .collect(Collectors.toList()));
        return teamMemberResponse;
    }
}
