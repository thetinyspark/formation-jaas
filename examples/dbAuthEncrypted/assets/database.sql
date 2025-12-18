CREATE DATABASE jaas_example;

USE jaas_example;

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE roles (
    role_name VARCHAR(50),
    username VARCHAR(50),
    FOREIGN KEY (username) REFERENCES users(username)
);

-- Ajout d'un utilisateur et de ses rôles
INSERT INTO users (username, password) VALUES ('admin', 'admin123');
INSERT INTO roles (role_name, username) VALUES ('admin', 'admin');
