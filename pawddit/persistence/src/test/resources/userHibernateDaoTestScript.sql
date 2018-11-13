CREATE TABLE IF NOT EXISTS users (
	userid INTEGER IDENTITY PRIMARY KEY,
	username VARCHAR(100),
	email VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	admin BOOLEAN NOT NULL,
	enabled BOOLEAN NOT NULL,
);

INSERT INTO users(userid, username, email, password, admin, enabled) VALUES (1, 'testUsername', 'testEmail', 'testPassword', FALSE, TRUE); 

