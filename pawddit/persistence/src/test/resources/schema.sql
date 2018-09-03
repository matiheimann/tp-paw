CREATE TABLE IF NOT EXISTS users (
	userid INTEGER IDENTITY PRIMARY KEY,
	username varchar(100)
);

CREATE TABLE IF NOT EXISTS POST(
	id SERIAL PRIMARY KEY,
	content text,
	creationDate timestamp NOT NULL,
	user int NOT NULL,
	groupName varchar(20) NOT NULL,
	FOREIGN KEY(user) REFERENCES USER(userid) ON DELETE CASCADE,
	FOREIGN KEY(groupName) REFERENCES GROUP(name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS GROUPS (
	name varchar(20) NOT NULL,
	owner int NOT NULL,
	description text,
	creationDate timestamp NOT NULL,
	PRIMARY KEY(name),
FOREIGN KEY(owner) REFERENCES USER(userid) ON DELETE CASCADE
);

