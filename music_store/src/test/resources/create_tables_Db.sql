/* Creating the database */
DROP DATABASE IF EXISTS beat_chamber;
CREATE DATABASE beat_chamber;

USE beat_chamber;

DROP USER IF EXISTS dawson@localhost;
CREATE USER dawson@'localhost' IDENTIFIED WITH mysql_native_password BY 'dawson123' REQUIRE NONE;
GRANT ALL ON beat_chamber.* TO dawson@'localhost';

/* remove tables if they are alredy present */
DROP TABLE IF EXISTS Ads;
DROP TABLE IF EXISTS RSS_Feeds;
DROP TABLE IF EXISTS Invoice_Details;
DROP TABLE IF EXISTS Survey_to_Choice;
DROP TABLE IF EXISTS Surveys;
DROP TABLE IF EXISTS Choices;
DROP TABLE IF EXISTS Artists_to_tracks;
DROP TABLE IF EXISTS Customer_reviews;
DROP TABLE IF EXISTS Tracks;
DROP TABLE IF EXISTS Artist_Albums;
DROP TABLE IF EXISTS Artists;
DROP TABLE IF EXISTS Albums;
DROP TABLE IF EXISTS Invoices;
DROP TABLE IF EXISTS Clients;
DROP TABLE IF EXISTS genre_to_album;
DROP TABLE IF EXISTS genre_to_tracks;
DROP TABLE IF EXISTS genres;



/* Artists */
create table Artists (
artist_id int not null primary key auto_increment,
artist_name varchar(40) not null
);

/* artists_to_tracks */
create table Artists_to_tracks(
tablekey int primary key auto_increment ,
artist_id int not null,
track_id int,
FOREIGN KEY (artist_id) REFERENCES Artists(artist_id)
);

/* Albums */
create table Albums (
album_number int not null primary key auto_increment,
album_title varchar(50) not null,
release_date date not null,
recording_label varchar(40) not null,
total_tracks int not null,
entry_date date not null,
cost_price double not null,
list_price double not null,
sale_price double not null,
removal_status boolean not null,
removal_date boolean
);



/* Tracks */
create table Tracks (
track_id int not null primary key auto_increment,
album_number int not null,
track_title varchar(40) not null,
play_length varchar(10) not null,
selection_number int not null,
music_category varchar(20) not null,
cost_price double not null,
list_price double not null,
sale_price double not null,
entry_date date not null,
removed boolean not null,
pst double not null,
gst double not null,
hst double not null,
FOREIGN KEY (album_number) REFERENCES Albums(album_number)
);


/*  genres */
create table genres(
genre_id int primary key auto_increment,
genre_name varchar(40) not null unique
);

/* genre_to_tracks */
create table genre_to_tracks(
tablekey int primary key auto_increment,
track_id int,
genre_id int,
FOREIGN KEY (track_id) REFERENCES Tracks(track_id),
FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

/* genre_to_album */
create table genre_to_album(
tablekey int primary key auto_increment,
album_number int,
genre_id int,
FOREIGN KEY (album_number) REFERENCES Albums(album_number),
FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);


/* Artist_Albums */
create table Artist_Albums(
tablekey int primary key auto_increment,
artist_id int not null ,
album_number int not null,
FOREIGN KEY (artist_id) REFERENCES Artists(artist_id),
FOREIGN KEY (album_number) REFERENCES Albums(album_number)
);

/* Clients */
create table Clients (
client_number int primary key auto_increment,
title varchar(40) not null,
last_name varchar(40) not null,
first_name varchar(40) not null,
company_name varchar(40) not null,
address1 varchar(40) not null,
address2 varchar(40) not null,
city varchar(20) not null,
province varchar(20) not null,
country varchar(40) not null,
postal_code varchar(20) not null,
home_phone varchar(13) not null,
cell_phone varchar(13) not null,
email varchar(40) not null,
genre_of_last_search varchar(40),
username varchar(40) not null,
password varchar(40) not null,
salt varchar(32),
hash varchar(32)
);

/* Customer_reviews */
create table Customer_reviews(
review_number int not null primary key auto_increment,
track_id int not null,
client_number int not null,
review_date date not null,
rating int not null,
review_text varchar(300) not null,
approval_status boolean not null,
FOREIGN KEY (track_id) REFERENCES Tracks(track_id),
FOREIGN KEY (client_number) REFERENCES Clients(client_number)
);

/* Invoices */
create table invoices(
sale_number int not null primary key auto_increment,
sale_date datetime not null,
client_number int not null,
total_net_value double not null,
PST double not null,
GST double not null,
HST double not null,
total_gross_value double not null,
FOREIGN KEY (client_number) REFERENCES Clients(client_number)
);

/* invoice details */
create table Invoice_Details(
tablekey int primary key auto_increment,
sale_number int not null,
track_id int not null,
PST double not null,
GST double not null,
HST double not null,
FOREIGN KEY (sale_number) REFERENCES invoices(sale_number),
FOREIGN KEY (track_id) REFERENCES Tracks(track_id)
);

/* Ads */
create table Ads (
ad_id int primary key auto_increment,
file_name varchar(60) not null,
link varchar(100) not null
);

/* RSS_Feeds */
create table RSS_Feeds (
rss_id int primary key auto_increment,
link varchar(100) not null
);

/* Surveys */
create table Surveys (
survey_id int primary key auto_increment,
title varchar(80) not null
);

/* Choices */
create table Choices (
choice_id int primary key auto_increment,
choice_name varchar(40) not null,
votes int not null
);

/* Survey_to_Choice */
create table Survey_to_Choice (
tablekey int primary key auto_increment,
survey_id int,
choice_id int,
FOREIGN KEY (survey_id) REFERENCES Surveys(survey_id),
FOREIGN KEY (choice_id) REFERENCES Choices(choice_id)
);






