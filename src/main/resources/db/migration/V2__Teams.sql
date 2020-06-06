CREATE TABLE teams
(
    team_id VARCHAR(255) NOT NULL,
    team_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (team_id)
);

CREATE TABLE team_members
(
    team_id         VARCHAR(255) NOT NULL,
    team_member_uid VARCHAR(255) NOT NULL,
    PRIMARY KEY (team_id, team_member_uid)
);