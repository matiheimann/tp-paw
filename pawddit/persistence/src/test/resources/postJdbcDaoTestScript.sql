CREATE TABLE IF NOT EXISTS users (
	userid INTEGER IDENTITY PRIMARY KEY,
	username VARCHAR(100),
	email VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	score INT
);

CREATE TABLE IF NOT EXISTS groups (
	name VARCHAR(100) PRIMARY KEY,
	creationdate TIMESTAMP NOT NULL,
	description VARCHAR(100) NOT NULL,
	owner INTEGER NOT NULL,
	FOREIGN KEY(owner) REFERENCES users(userid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS posts (
	postid INTEGER IDENTITY PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	content VARCHAR(100) NOT NULL,
	creationdate TIMESTAMP NOT NULL,
	groupname VARCHAR(100) NOT NULL,
	userid INTEGER NOT NULL,
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupName) REFERENCES groups(name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subscriptions (
	subscriptionid INTEGER IDENTITY PRIMARY KEY,	
	userid INTEGER NOT NULL,
	groupname VARCHAR(20) NOT NULL,
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupname) REFERENCES groups(name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
	commentid INTEGER IDENTITY PRIMARY KEY,
	content VARCHAR(100) NOT NULL,
	replyto INTEGER,
	postid INTEGER NOT NULL,
	userid INTEGER NOT NULL,
	creationdate TIMESTAMP,
	FOREIGN KEY (postid) REFERENCES posts(postid) ON DELETE CASCADE,
	FOREIGN KEY (userid) REFERENCES users(userid) ON DELETE CASCADE
);

INSERT INTO users VALUES (1, 'testUser', 'testEmail', 'testPassword', 0);
INSERT INTO groups VALUES ('testGroup', '2018-09-21 19:15:40.5', 'testDescription', 1);
INSERT INTO posts VALUES (1, 'testPost1', 'testContent', '2018-09-21 19:15:40.5', 'testGroup', 1);
INSERT INTO posts VALUES (2, 'testPost2', 'testContent', '2018-09-21 19:15:40.5', 'testGroup', 1);
INSERT INTO posts VALUES (3, 'testPost3', 'testContent', '2018-09-21 19:15:40.5', 'testGroup', 1);
INSERT INTO subscriptions VALUES (1, 1, 'testGroup');