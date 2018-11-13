CREATE TABLE IF NOT EXISTS images (
	token VARCHAR(20) NOT NULL PRIMARY KEY,
	bytearray binary NOT NULL
);

INSERT INTO images VALUES('token_1', X'00');