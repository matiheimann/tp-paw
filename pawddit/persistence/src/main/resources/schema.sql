CREATE TABLE IF NOT EXISTS users (
	username varchar(100) NOT NULL,
	userid SERIAL PRIMARY KEY,
	email text NOT NULL,
	password text NOT NULL,
	score int
);
