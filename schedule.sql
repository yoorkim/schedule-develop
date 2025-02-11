CREATE TABLE member
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) UNIQUE NOT NULL,
    member_name VARCHAR(255) NOT NULL,
    pwd         VARCHAR(255) NOT NULL,
    created_at  DATETIME(6) NULL,
    modified_at DATETIME(6) NULL
);

CREATE TABLE schedule
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    contents    VARCHAR(255) NULL,
    member_id   BIGINT NOT NULL,
    created_at  DATETIME(6) NULL,
    modified_at DATETIME(6) NULL,
    CONSTRAINT FK_schedule_member FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE comment
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    contents    VARCHAR(255) NOT NULL,
    member_id   BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    created_at  DATETIME(6) NULL,
    modified_at DATETIME(6) NULL,
    CONSTRAINT FK_comment_member FOREIGN KEY (member_id) REFERENCES member(id),
    CONSTRAINT FK_comment_schedule FOREIGN KEY (schedule_id) REFERENCES schedule(id)
);
