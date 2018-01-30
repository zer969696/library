-- schema.sql
DROP TABLE IF EXISTS person;

CREATE TABLE users(
  id NUMERIC IDENTITY PRIMARY KEY,
  name VARCHAR(64) NOT NULL
);

CREATE TABLE books(
  id NUMERIC IDENTITY PRIMARY KEY,
  author VARCHAR(128) NOT NULL,
  title VARCHAR(128) NOT NULL,
  user_id INTEGER
);

CREATE TABLE user_account(
  id NUMERIC IDENTITY PRIMARY KEY,
  login VARCHAR(128) NOT NULL,
  password VARCHAR(128) NOT NULL,
  user_id INTEGER UNIQUE
);

ALTER TABLE user_account ADD CONSTRAINT FK_ACCOUNT_USER FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE books ADD CONSTRAINT FK_USER_BOOK FOREIGN KEY (user_id) REFERENCES users(id)