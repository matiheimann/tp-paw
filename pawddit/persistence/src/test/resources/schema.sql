CREATE TABLE IF NOT EXISTS users (
	userid INTEGER IDENTITY PRIMARY KEY,
	username VARCHAR(100),
	email VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	score INT
);

INSERT INTO users VALUES (1, 'testUsername', 'testEmail', 'testPassword', 0);

