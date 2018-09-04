CREATE TABLE IF NOT EXISTS users (
	username varchar(100) NOT NULL,
	userid SERIAL PRIMARY KEY,
	email text NOT NULL,
	password text NOT NULL,
	score int
);

CREATE TABLE IF NOT EXISTS groups(
	name TEXT PRIMARY KEY,
	creationDate Timestamp NOT NULL,
	description TEXT NOT NULL,
	owner int NOT NULL,
	FOREIGN KEY(owner) REFERENCES users(userid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS posts(
	postId SERIAL PRIMARY KEY,
	content TEXT NOT NULL,
	creationDate Timestamp NOT NULL,
	groupName TEXT NOT NULL,
	userId int NOT NULL,
	FOREIGN KEY(userId) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupName) REFERENCES groups(name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments(
	commentId SERIAL PRIMARY KEY,
	content TEXT NOT NULL,
	replyTo int NOT NULL,
	postId int NOT NULL,
	userId int NOT NULL,
	creationDate Timestamp,
	FOREIGN KEY (postId) REFERENCES posts(postId) ON DELETE CASCADE,
	FOREIGN KEY (userId) REFERENCES users(userid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subscriptions(
	subscriptionId SERIAL PRIMARY KEY,	
	userId int NOT NULL,
	groupName varchar(20) NOT NULL,
	FOREIGN KEY(userId) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupName) REFERENCES groups(name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS voteposts (
	votePostsId SERIAL PRIMARY KEY,
	valueVote int NOT NULL,
	postId int NOT NULL,
	userId int NOT NULL,
	FOREIGN KEY(userID) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(postId) REFERENCES posts(postId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS votecomments (
	voteCommentId SERIAL PRIMARY KEY,
	valueVote int NOT NULL,
	commentId int NOT NULL,
	userId int NOT NULL,
	FOREIGN KEY(userId) REFERENCES users(userid) ON DELETE CASCADE,
	FOREIGN KEY(commentId) REFERENCES comments(commentId) ON DELETE CASCADE
);






