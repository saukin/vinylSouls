DROP DATABASE IF EXISTS XS_PROJECT;
CREATE DATABASE XS_PROJECT;

USE XS_PROJECT;

DROP USER IF EXISTS access@localhost;
CREATE USER access@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin' REQUIRE NONE;
GRANT ALL ON XS_PROJECT.* TO access@'localhost';

FLUSH PRIVILEGES;


DROP TABLE IF EXISTS APPUSERS;
CREATE TABLE APPUSERS (
USER_ID int(8) NOT NULL UNIQUE auto_increment,
EMAIL varchar(25) NOT NULL UNIQUE,
PASSWORD varchar(25) NOT NULL,
PRIMARY KEY (USER_ID)
);

INSERT INTO APPUSERS (EMAIL, PASSWORD) values
("SomeUser","Password01"),
("SomeUser2","Password02");


DROP TABLE IF EXISTS APPALBUMS;
CREATE TABLE APPALBUMS (
ENTRY int(8) NOT NULL UNIQUE auto_increment,
ALBUM_NAME varchar(25) NOT NULL,
UPC_CODE varchar(25),
PRESSING_YEAR int(4),
ARTIST_GROUP varchar(25) NOT NULL,
CONDITION_STATE varchar(4),
NOTES varchar(400),
USER_OWNERSHIP int(8)  NOT NULL,
ACTIVE boolean NOT NULL,
PRIMARY KEY (ENTRY),
CONSTRAINT FOREIGN KEY (USER_OWNERSHIP) REFERENCES APPUSERS(USER_ID)
);

INSERT INTO APPALBUMS (ALBUM_NAME, UPC_CODE, PRESSING_YEAR, ARTIST_GROUP, CONDITION_STATE, NOTES, USER_OWNERSHIP, ACTIVE) values
("Face the Heat","UPC1", 1994, "SCORPIONS", "M+", "This album marked their status as a sort of political band with the song Alien Nation, which was about the re-unification of Germany. This album had a contemporary touch to it, as the band were then going with the current trends, before later returning to their original style in Unbreakable in 2004", 1, TRUE),
("Peyote Dance","UPC2", 2019, "Soundwalk", "M+", "This album is a product of Soundwalk Collective and Patty Smith", 2, TRUE),
("Cest Com Com Complique","UPC3", 2009, "Faust", "M-", "", 1, TRUE),
("Piano Works IX","UPC4", 2009, "Joachim Kuhn", "M+", "Joachim Kuhn and Michael Wollny", 1, FALSE),
("First Chapter","UPC5", 2013, "SomeGroup", "M", "Murcof & Philippe Petit. The best duo!!", 1, FALSE);

