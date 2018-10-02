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

CREATE TABLE IF NOT EXISTS images (
	token VARCHAR(100) NOT NULL PRIMARY KEY,
	bytearray binary NOT NULL
);

CREATE TABLE IF NOT EXISTS posts (
	postid INTEGER IDENTITY PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	content VARCHAR(100) NOT NULL,
	creationdate TIMESTAMP NOT NULL,
	groupname VARCHAR(100) NOT NULL,
	userid INTEGER NOT NULL,
	imageid VARCHAR(100),
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupName) REFERENCES groups(name) ON DELETE CASCADE,
	FOREIGN KEY(imageid) REFERENCES images(token)
);

CREATE TABLE IF NOT EXISTS voteposts (
	valuevote INT NOT NULL,
	postid INT NOT NULL,
	userid INT NOT NULL,
	PRIMARY KEY(userid, postid),
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(postid) REFERENCES posts(postid) ON DELETE CASCADE
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

INSERT INTO users VALUES (1, 'votingUser', 'testEmail1', 'testPassword', 0, FALSE, TRUE);
INSERT INTO users VALUES (2, 'nonVotingUser', 'testEmail2', 'testPassword', 0, FALSE, TRUE);
INSERT INTO users VALUES (3, 'postingUser', 'testEmail3', 'testPassword', 0, FALSE, TRUE);
INSERT INTO groups VALUES ('testGroup', '2018-09-21 19:15:40.5', 'testDescription', 1);
INSERT INTO posts VALUES (1, 'testPost1', 'testContent', '2018-09-21 19:15:40.5', 'testGroup', 3, NULL);
INSERT INTO voteposts VALUES(1, 1, 1);