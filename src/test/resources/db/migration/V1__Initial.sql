CREATE TABLE retro_boards
(
    id        VARCHAR(255) NOT NULL,
    name      VARCHAR(255) NOT NULL,
    blur      VARCHAR(5)   NOT NULL,
    max_likes INT
);

CREATE TABLE retro_walls
(
    retro_board_id VARCHAR(255) NOT NULL,
    wall_id        VARCHAR(255) NOT NULL,
    title          VARCHAR(255) NOT NULL,
    wall_style     VARCHAR(255),
    sort_cards     BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (wall_id)
);

CREATE TABLE wall_styles
(
    wall_id       VARCHAR(255) NOT NULL,
    wall_style_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (wall_style_id)
);

CREATE TABLE sticky_note_styles
(
    sticky_note_style_id VARCHAR(255) NOT NULL,
    wall_style_id        VARCHAR(255) NOT NULL,
    background_color     VARCHAR(55)  NOT NULL,
    text_color           VARCHAR(55)  NOT NULL,
    like_btn_position    VARCHAR(55)  NOT NULL,
    PRIMARY KEY (sticky_note_style_id)
);

CREATE TABLE notes
(
    note_id    VARCHAR(255)  NOT NULL,
    note_text  VARCHAR(1024) NOT NULL,
    board_id   VARCHAR(255)  NOT NULL,
    wall_id    VARCHAR(255)  NOT NULL,
    created_by VARCHAR(55)   NOT NULL
);

CREATE TABLE users
(
    uid          VARCHAR(255),
    username     VARCHAR(255),
    display_name VARCHAR(99),
    email        VARCHAR(255)
);

CREATE TABLE user_boards
(
    uid      VARCHAR(255),
    board_id VARCHAR(255)
);