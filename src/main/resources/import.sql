CREATE TABLE IF NOT EXISTS users (
   id VARCHAR(255),
   username VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   isAdmin BOOLEAN DEFAULT FALSE,
   PRIMARY KEY (id)
);

INSERT INTO users (id, username, password, isAdmin)
    SELECT '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', 'admin', 'admin', TRUE
    WHERE NOT EXISTS(SELECT * FROM users WHERE id='b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d');