CREATE TABLE shared_items
(
    item_id          VARCHAR(255) NOT NULL,
    team_id          VARCHAR(255) NOT NULL,
    PRIMARY KEY (item_id, team_id)
);