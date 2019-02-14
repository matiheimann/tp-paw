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