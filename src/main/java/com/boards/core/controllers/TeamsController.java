package com.boards.core.controllers;

import com.boards.core.model.dto.TeamMemberRequest;
import com.boards.core.model.dto.teams.*;
import com.boards.core.model.entities.retroboard.User;
import com.boards.core.services.TeamsService;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static com.boards.core.configuration.AppConfig.*;
import static com.boards.core.configuration.AppUtil.getLoggedInUser;
import static org.springframework.http.ResponseEntity.*;

@Log4j
@CrossOrigin(exposedHeaders = {EXPOSE_ACCESS_TOKEN, EXPOSE_LOCATION, EXPOSE_UID})
@RestController
@RequestMapping("/teams")
public class TeamsController {

    private TeamsService teamsService;

    public TeamsController(TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    @PostMapping
    public ResponseEntity<URI> createNewTeam(@RequestBody TeamRequest teamRequest) {
        URI location = teamsService.createNewTeam(getLoggedInUser(), teamRequest);
        return created(location).build();
    }

    @GetMapping
    public ResponseEntity<TeamListResponse> getMyTeams() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ok(teamsService.getMyTeams(loggedInUser));
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable String teamId) {
        Optional<TeamResponse> teamResponse = teamsService.getTeam(teamId);
        return teamResponse.isPresent() ? ok(teamResponse.get()) : notFound().build();
    }

    @PostMapping("/member")
    public ResponseEntity<URI> addNewTeamMember(@RequestBody AddTeamMemberRequest addTeamMemberRequest) {
        log.info("<addNewTeamMember>: Request: " + addTeamMemberRequest);
        URI location = teamsService.addTeamMember(addTeamMemberRequest);
        return created(location).build();
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<TeamMemberListResponse> getTeamMembers(@PathVariable String teamId) {
        TeamMemberListResponse teamMemberListResponse = teamsService.getTeamMembers(teamId);
        return ok(teamMemberListResponse);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable String teamId) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        teamsService.deleteTeam(teamId, loggedInUser);
        return noContent().build();
    }

    @DeleteMapping("/{teamId}/member/{uid}")
    public ResponseEntity<HttpStatus> deleteTeamMember(@PathVariable String teamId, @PathVariable String uid) {
        teamsService.removeTeamMember(getLoggedInUser(), teamId, uid);
        return ResponseEntity.noContent().build();
    }


}
