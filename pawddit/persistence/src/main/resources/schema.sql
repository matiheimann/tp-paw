CREATE TABLE IF NOT EXISTS users (
	userid SERIAL PRIMARY KEY,
	username VARCHAR(100) UNIQUE NOT NULL,
	email TEXT NOT NULL,
	password TEXT NOT NULL,
	score INT,
	enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS groups (
	name TEXT PRIMARY KEY,
	creationdate TIMESTAMP NOT NULL,
	description TEXT NOT NULL,
	owner INT NOT NULL,
	FOREIGN KEY(owner) REFERENCES users(userid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS images (
	token TEXT NOT NULL PRIMARY KEY,
	bytearray BYTEA NOT NULL
);

CREATE TABLE IF NOT EXISTS posts (
	postid SERIAL PRIMARY KEY,
	title TEXT NOT NULL,
	content TEXT NOT NULL,
	creationdate TIMESTAMP NOT NULL,
	groupname TEXT NOT NULL,
	userid INT NOT NULL,
	imageid TEXT,
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupName) REFERENCES groups(name) ON DELETE CASCADE,
	FOREIGN KEY(imageid) REFERENCES images(token)
);

CREATE TABLE IF NOT EXISTS comments (
	commentid SERIAL PRIMARY KEY,
	content TEXT NOT NULL,
	replyto INT,
	postid INT NOT NULL,
	userid INT NOT NULL,
	creationdate TIMESTAMP,
	FOREIGN KEY (postid) REFERENCES posts(postid) ON DELETE CASCADE,
	FOREIGN KEY (userid) REFERENCES users(userid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subscriptions (	
	userid INT NOT NULL,
	groupname VARCHAR(20) NOT NULL,
	PRIMARY KEY(userid, groupname),
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupname) REFERENCES groups(name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS voteposts (
	valuevote INT NOT NULL,
	postid INT NOT NULL,
	userid INT NOT NULL,
	PRIMARY KEY(userid, postid),
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(postid) REFERENCES posts(postid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS votecomments (
	valuevote INT NOT NULL,
	commentid INT NOT NULL,
	userid INT NOT NULL,
	PRIMARY KEY(userid, commentid),
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(commentid) REFERENCES comments(commentid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS verificationtokens (
	token TEXT NOT NULL PRIMARY KEY,
	userid INT NOT NULL,
	FOREIGN KEY(userid) REFERENCES users(userid) ON DELETE CASCADE
);