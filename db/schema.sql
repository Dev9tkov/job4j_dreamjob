DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS candidate;
DROP TABLE IF EXISTS client;
CREATE TABLE post (id SERIAL PRIMARY KEY, name TEXT);
CREATE TABLE candidate (id SERIAL PRIMARY KEY, name TEXT, photoId TEXT);
CREATE TABLE client (id SERIAL PRIMARY KEY, name TEXT, email TEXT, password TEXT);