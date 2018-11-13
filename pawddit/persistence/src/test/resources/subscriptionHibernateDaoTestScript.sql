CREATE TABLE IF NOT EXISTS users (
	userid INTEGER IDENTITY PRIMARY KEY,
	username VARCHAR(100),
	email VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	admin BOOLEAN NOT NULL,
	enabled BOOLEAN NOT NULL,
);

CREATE TABLE IF NOT EXISTS groups (
	name VARCHAR(100) PRIMARY KEY,
	creationdate TIMESTAMP NOT NULL,
	description VARCHAR(100) NOT NULL,
	owner INTEGER NOT NULL,
);

INSERT INTO users(userid, username, email, password, admin, enabled) VALUES (1, 'subscribedUser', 'testEmail1', 'testPassword1', FALSE, TRUE);
INSERT INTO users(userid, username, email, password, admin, enabled) VALUES (2, 'unsubscribedUser', 'testEmail2', 'testPassword2', FALSE, TRUE);  
INSERT INTO groups(name, creationdate, description, owner) VALUES ('testGroup', '2018-09-21 19:15:40.5', 'testDescription', 1);

