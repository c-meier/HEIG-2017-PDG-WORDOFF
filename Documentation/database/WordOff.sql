--
-- Database: WordOff
--

DROP SCHEMA IF EXISTS WordOff;
CREATE SCHEMA WordOff;

USE WordOff;
SET foreign_key_checks = 0;

CREATE TABLE User (
	username VARCHAR(15) PRIMARY KEY,
    pass_hash binary(32), #Ici SHA-1, 64 pour SHA-256, à voir
	pass_sale binary(128), #128bit salts comme des bg
    creation_date Date,
    nb_stars INT,
    nb_coins INT,
    avatar_id INT,
    FOREIGN KEY (avatar_id) REFERENCES Avatars(avatar_id),
    FOREIGN KEY (username) REFERENCES Player(name)
);

CREATE TABLE Friends (
	user1 VARCHAR(15),
    user2 VARCHAR(15),
    PRIMARY KEY (user1, user2),
    FOREIGN KEY (user1) REFERENCES User(username),
    FOREIGN KEY (user2) REFERENCES User(username)
);

CREATE TABLE Blacklist (
	user1 VARCHAR(15),
    blacklisted_user VARCHAR(15),
    PRIMARY KEY (user1, blacklisted_user),
    FOREIGN KEY (user1) REFERENCES User(username),
    FOREIGN KEY (blacklisted_user) REFERENCES User(username)
);

CREATE TABLE Game (
	game_id INT AUTO_INCREMENT PRIMARY KEY,
    side1 INT,
    side2 INT,
	start_date Date,
    nb_hint INT,
    FOREIGN KEY (side1) REFERENCES Side(side_id),
    FOREIGN KEY (side2) REFERENCES Side(side_id)
);

CREATE TABLE Side (
	side_id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE Bag (
	game_id INT,
    tile_id INT,
	FOREIGN KEY (game_id) REFERENCES Game(game_id),
    FOREIGN KEY (tile_id) REFERENCES Tile(tile_id)

);

#Attributs uniques au mode tournoi. A discuter
CREATE TABLE Tournament (
	mode_id INT AUTO_INCREMENT PRIMARY KEY,
    start_date Date,
    current_day INT,
    FOREIGN KEY (mode_id) REFERENCES Mode(mode_id)
);

CREATE TABLE Tileset (
	name VARCHAR(30) PRIMARY KEY,
    language VARCHAR(30),
    FOREIGN KEY (language) REFERENCES Language(name)
);

CREATE TABLE Language (
	name VARCHAR(30) PRIMARY KEY
);

CREATE TABLE Tile (
	tile_id INT PRIMARY KEY AUTO_INCREMENT,
    tileset_name VARCHAR(30),
    letter CHAR,
    FOREIGN KEY (tileset_name) REFERENCES Tileset(name),
    FOREIGN KEY (letter) REFERENCES Letter(letter)
);

CREATE TABLE Rack (
	side_id INT,
    tile_id INT,
    PRIMARY KEY (side_id, tile_id),
    FOREIGN KEY (side_id) REFERENCES Side(side_id),
    FOREIGN KEY (tile_id) REFERENCES Tile(tile_id)
);

CREATE TABLE SwapRack (
	side_id INT,
    tile_id INT,
    PRIMARY KEY (side_id, tile_id),
    FOREIGN KEY (side_id) REFERENCES Side(side_id),
    FOREIGN KEY (tile_id) REFERENCES Tile(tile_id)
);

CREATE TABLE Letter (
    letter CHAR PRIMARY KEY,
    point_value INT
);

CREATE TABLE Participation (
	mode_id INT,
    user_id VARCHAR(15),
    PRIMARY KEY (mode_id, user_id),
    FOREIGN KEY (mode_id) REFERENCES Mode(mode_id),
    FOREIGN KEY (user_id) REFERENCES User(username)
);

CREATE TABLE Message (
	message_id INT PRIMARY KEY AUTO_INCREMENT,
    create_date Date,
    content NVARCHAR(512), #Caractères unicode donc avec accents entre autres
	sender VARCHAR(15),
    game_id INT,
    FOREIGN KEY (sender) REFERENCES User(username),
    FOREIGN KEY (game_id) REFERENCES Game(game_id)
);

CREATE TABLE Slot (
	side_id INT,
    position INT,
    #type?
    PRIMARY KEY (side_id, position),
    FOREIGN KEY (side_id) REFERENCES Side(side_id)
);

CREATE TABLE Player (
	name VARCHAR(15),
	FOREIGN KEY (name) REFERENCES User(username)
);

CREATE TABLE Invitation (
	mode_id INT,
	user1 VARCHAR(15),
    user2 VARCHAR(15),
    PRIMARY KEY (mode_id, user1, user2),
    FOREIGN KEY (user1) REFERENCES User(username),
    FOREIGN KEY (user2) REFERENCES User(username),
    FOREIGN KEY (mode_id) REFERENCES Mode(mode_id)
);

CREATE TABLE Answer (
	side_id int,
    num int,
    word VARCHAR(7),
    score INT,
    PRIMARY KEY (side_id, num),
    FOREIGN KEY (side_id) REFERENCES Side(side_id)
);
   
