DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS candidate;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS country;

CREATE TABLE post (id SERIAL PRIMARY KEY, name TEXT);
CREATE TABLE candidate (id SERIAL PRIMARY KEY, name TEXT, photoId TEXT, country TEXT, city TEXT);
CREATE TABLE client (id SERIAL PRIMARY KEY, name TEXT, email TEXT, password TEXT);

CREATE TABLE IF NOT EXISTS country (id SERIAL PRIMARY KEY, name TEXT);
CREATE TABLE IF NOT EXISTS city (id SERIAL PRIMARY KEY, name TEXT, country_id INT REFERENCES country(id) ON UPDATE CASCADE ON DELETE CASCADE);

INSERT INTO country(name) VALUES ('Russia'), ('Ukraine');
INSERT INTO city(name, country_id) VALUES ('Perm', 1), ('Kazan', 1), ('Moscow', 1), ('Odessa', 2), ('Kharkiv', 2), ('Donetsk', 2);