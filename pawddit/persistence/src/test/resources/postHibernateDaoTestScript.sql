CREATE TABLE IF NOT EXISTS users (
	userid INTEGER IDENTITY PRIMARY KEY,
	username VARCHAR(100),
	email VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
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

INSERT INTO users(userid, username, email, password, admin, enabled) VALUES (1, 'testUser', 'testEmail', 'testPassword', FALSE, TRUE);
INSERT INTO groups(name, creationdate, description, owner) VALUES ('testGroup', '2018-09-21 19:15:40.5', 'testDescription', 1);
INSERT INTO posts(postid, title, content, creationdate, groupname, userid, imageid) VALUES (1, 'testPost1', 'testContent', '2018-09-21 19:15:40.5', 'testGroup', 1, NULL);
INSERT INTO posts(postid, title, content, creationdate, groupname, userid, imageid) VALUES (2, 'testPost2', 'testContent', '2018-09-21 19:15:40.5', 'testGroup', 1, NULL);
INSERT INTO posts(postid, title, content, creationdate, groupname, userid, imageid) VALUES (3, 'testPost3', 'testContent', '2018-09-21 19:15:40.5', 'testGroup', 1, NULL);
