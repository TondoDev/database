CREATE TABLE Person (
	persid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	name VARCHAR NOT NULL,
	birthdate DATE NOT NULL,
	lastlogon TIMESTAMP,
	weight REAL
);