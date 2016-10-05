CREATE TABLE Person (
	persid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	name VARCHAR NOT NULL,
	birthdate DATE NOT NULL,
	lastlogon TIMESTAMP,
	weight REAL
);

INSERT INTO Person(name, birthdate, lastlogon, weight) VALUES('Peter', '1988-05-07', null, 92.8);