CREATE TABLE IF NOT EXISTS USERS (
    id SERIAL PRIMARY KEY,
    nickname VARCHAR(5),
    age BIGINT
);

INSERT INTO USERS(nickname, age) VALUES ('terry', 2);
INSERT INTO USERS(nickname, age) VALUES ('James', 3);