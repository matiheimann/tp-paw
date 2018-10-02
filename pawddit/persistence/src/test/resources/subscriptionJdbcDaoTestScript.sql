CREATE TABLE IF NOT EXISTS users (
	userid INTEGER IDENTITY PRIMARY KEY,
	username VARCHAR(100),
	email VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	score INTEGER,
	admin BOOLEAN NOT NULL,
	enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS groups (
	name VARCHAR(100) PRIMARY KEY,
	creationdate TIMESTAMP NOT NULL,
	description VARCHAR(100) NOT NULL,
	owner INTEGER NOT NULL,
	FOREIGN KEY(owner) REFERENCES users(userid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subscriptions (
	subscriptionid INTEGER IDENTITY PRIMARY KEY,	
	userid INTEGER NOT NULL,
	groupname VARCHAR(20) NOT NULL,
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupname) REFERENCES groups(name) ON DELETE CASCADE
);

INSERT INTO users VALUES (1, 'subscribedUser', 'testEmail', 'testPassword', 0, FALSE, TRUE);
INSERT INTO users VALUES (2, 'unsubscribedUser', 'testEmail', 'testPassword', 0, FALSE, TRUE);  
INSERT INTO groups VALUES ('testGroup', '2018-09-21 19:15:40.5', 'testDescription', 1);
INSERT INTO subscriptions VALUES (1, 1, 'testGroup');
