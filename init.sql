--DROP DATABASE IF EXISTS db;

--
--CREATE DATABASE db;


CREATE TABLE IF NOT EXISTS  items (
    id SERIAL PRIMARY KEY,
    type VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    parent_id  int     REFERENCES items,
    permission_group_id INT
);

CREATE TABLE IF NOT EXISTS files (
    id SERIAL PRIMARY KEY,
    binary_file BYTEA NOT NULL,
    item_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS permission_groups (
    id SERIAL PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS permissions (
    id SERIAL PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    permission_level VARCHAR(255) NOT NULL,
    group_id INT NOT NULL
);

ALTER TABLE items
ADD CONSTRAINT fk_permission_group
FOREIGN KEY (permission_group_id)
REFERENCES permission_groups(id);

ALTER TABLE files
ADD CONSTRAINT fk_item
FOREIGN KEY (item_id)
REFERENCES items(id);


ALTER TABLE permissions
ADD CONSTRAINT fk_group
FOREIGN KEY (group_id)
REFERENCES permission_groups(id);





