CREATE TABLE templates
(
    template_id VARCHAR(255) NOT NULL,
    template_title VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (template_id)
);

CREATE TABLE template_walls
(
    template_id VARCHAR(255) NOT NULL,
    wall_id VARCHAR(255) NOT NULL,
    wall_title VARCHAR(255) NOT NULL,
    wall_order INT NOT NULL,
    PRIMARY KEY (wall_id)
);

CREATE TABLE template_wall_sticky_note_styles
(
    template_wall_id        VARCHAR(255) NOT NULL,
    background_color     VARCHAR(55)  NOT NULL,
    text_color           VARCHAR(55)  NOT NULL,
    like_btn_position    VARCHAR(55)  NOT NULL,
    PRIMARY KEY (template_wall_id)
);