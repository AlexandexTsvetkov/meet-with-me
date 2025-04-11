CREATE TABLE IF NOT EXISTS app_user (
     id SERIAL PRIMARY KEY,
         email VARCHAR(255) NOT NULL UNIQUE,
         name VARCHAR(255) NOT NULL,
         password VARCHAR(255) NOT NULL,
         role VARCHAR(20) DEFAULT 'USER'
    );

CREATE TABLE IF NOT EXISTS meeting (
    id SERIAL PRIMARY KEY,
    create_on TIMESTAMP DEFAULT (CURRENT_DATE),
    description VARCHAR(255),
    meeting_date TIMESTAMP,
    initiator_id BIGINT REFERENCES app_user(id),
    location VARCHAR(255),
    title VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS invitations (
    id SERIAL PRIMARY KEY,
    meeting_id BIGINT NOT NULL,
    invited_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,

    CONSTRAINT fk_requests_meeting_id FOREIGN KEY (meeting_id) REFERENCES meeting (id) ON DELETE CASCADE,
    CONSTRAINT fk_requests_invited_id FOREIGN KEY (invited_id) REFERENCES app_user (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS meeting_participant (
    id SERIAL PRIMARY KEY,
    meeting_id BIGINT NOT NULL REFERENCES meeting(id),
    user_id BIGINT NOT NULL REFERENCES app_user(id)
);