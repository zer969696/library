-- schema.sql
DROP TABLE IF EXISTS person;

CREATE TABLE person(
  id NUMERIC IDENTITY PRIMARY KEY,
  first_name VARCHAR(512) NOT NULL,
  last_name VARCHAR(512) NOT NULL,
  date_of_birth TIMESTAMP NOT NULL,
  place_of_birth VARCHAR(512)
);