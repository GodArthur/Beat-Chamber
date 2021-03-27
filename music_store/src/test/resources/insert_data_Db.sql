USE CSgb1w21;
--USE beat_chamber;

insert into genres (genre_name) values
('Hip Hop'),
('Rock'),
('Movie Soundtracks'),
('Jazz'),
('Classical')
;

insert into Albums (album_title,release_date,recording_label,total_tracks,entry_date,cost_price,list_price,sale_price,removal_status,removal_date) values 
('Please Excuse Me For Being Antisocial','2019-12-06','Atlantic Records',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--1
('Year Of The Gentleman','2008-08-11','Def Jam Japan',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--2
('After Hours','2020-03-20','XO and Republic Records',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--3
('If You''re Reading This It''s Too Late','2015-02-13','OVO Sound',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--4
('ASTROWORLD','2018-09-03','Epic Records',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--5
('BOSSANOVA','1990-08-13','4AD',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--6
('NEVERMIND','1991-09-24','Geffen Records',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--7
('THE DARK SIDE OF THE MOON','1973-03-01','Capitol Records',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--8
('TUBULAR BELLS','1973-05-25','Virgin Records',4,CURRENT_DATE(),5.00,10.00,15.00,false,null),--9
('OK COMPUTER','1997-5-21','Parlophone',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--10
('The Dark Knight','2008-07-15','Reprise Records',4,CURRENT_DATE(),5.00,10.00,15.00,false,null),--11
('The Mandalorian Season 2 vol.2','2020-12-18','WALT DISNEY RECORDS',4,CURRENT_DATE(),5.00,10.00,15.00,false,null),--12
('Star Wars: The Empire Strikes Back','1980-05-16','RSO Records',4,CURRENT_DATE(),5.00,10.00,15.00,false,null),--13
('Harry Potter and The Sorcerer''s Stone','2001-10-30','Warner Sunset RECORDS',4,CURRENT_DATE(),5.00,10.00,15.00,false,null),--14
('Spider-man: Far From Home','2019-06-28','Sony Classical',4,CURRENT_DATE(),5.00,10.00,15.00,false,null),--15
('Come Away With Me','2002-02-26','Blue Note Records',4,CURRENT_DATE(),5.00,10.00,15.00,false,null),--16
('What A Wonderful World','1967-08-1','Universal Music Group',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--17
('Time Out','1959-12-14','cbs',5,CURRENT_DATE(),5.00,10.00,15.00,false,null),--18
('Wallflower','2015-02-03','Virgin Records America Inc',6,CURRENT_DATE(),5.00,10.00,15.00,false,null),--19
('Piano Piano','2021-01-22','Dualtone Records',6,CURRENT_DATE(),5.00,10.00,15.00,false,null),--20
('10','2020-10-23','Sony Music Masterworks',6,CURRENT_DATE(),5.00,10.00,15.00,false,null),--21
('Bones in the Ocean','2013-05-27','The Longest Johns',6,CURRENT_DATE(),5.00,10.00,15.00,false,null),--22
('Pure Piano Improv','2015-12-04','Musser Music',6,CURRENT_DATE(),5.00,10.00,15.00,false,null),--23
('Tree of Life','2013-07-16','Lakeshore Records',6,CURRENT_DATE(),5.00,10.00,15.00,false,null);--24


insert into Artists (artist_name) values
('Roddy Ricch'), /* Please Excuse Me For Being Antisocial 1*/
('Ne-Yo'), /* Year Of The Gentleman  2*/
('The Weeknd'), /* After Hours 3*/
('Drake'), /* If Youre Reading This It's Too Late 4*/
('Travis Scott'), /* ASTROWORLD 5*/
('Pixies'), /* BOSSANOVA 6*/
('Nirvana'), /* NEVERMIND  7*/
('Pink Floyd'), /* THE DARK SIDE OF THE MOON 8*/
('Mike Oldfield'), /* TUBULAR BELLS 9*/
('Radiohead'), /* OK COMPUTER 10*/
('Hans Zimmer'),/* The Dark Knight 11*/
('James Newton Howard'), /* The Dark Knight 12*/
('Ludwig Göransson'),/* The Mandalorian Season 2 vol.2 13*/
('Orchestre symphonique de Londres'), /* Star Wars: The Empire Strikes Back album artist 14*/
('John Williams'), /* Harry Potter and The Sorcerer s Stone 15*/ 
('Michael Giacchino'), /* Spider-man: Far From Home 16*/
('Norah Jones'), /* COME AWAY WITH ME 17*/
('Louis Armstrong'),/* WHAT A WONDERFUL WORLD 18*/
('Dave Brubeck'), /* TIME OUT 19*/
('Diana Krall'),/* WALLFLOWER  20*/
('Jeremiah Fraites'), /* Piano Piano 21*/
('The Piano Guys'), /* 10 22*/
('The Long Johns'), /* Bones in the Ocean 23*/
('Brandon Musser'), /* Pure Piano Improv, Pt.2 24*/
('Audiomachine') /* Tree of Life 25*/;



insert into Artist_Albums (artist_id,album_number) values
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10),
(11,11),
(12,11),
(13,12),
(14,13),
(15,14),
(16,15),
(17,16),
(18,17),
(19,18),
(20,19),
(21,20),
(22,21),
(23,22),
(24,23),
(25,24);





insert into Tracks (album_number,track_title,play_length,selection_number,music_category,cost_price,list_price,sale_price,entry_date,removed,pst,gst,hst) values
(1,'The Box','9:10',1,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(1,'Boom Boom Room','9:10',1,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(1,'Roll Dice','9:10',1,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(1,'High Fashion','9:10',1,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(1,'Bacc Seat','9:10',1,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(2,'Closer','9:10',2,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(2,'Single','9:10',2,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(2,'Mad','9:10',2,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(2,'Miss Independent','9:10',2,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(2,'Fade Into The Background','9:10',2,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(3,'Snow Child','9:10',3,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(3,'Escape From LA','9:10',3,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(3,'After Hours','9:10',3,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(3,'Blinding Lights','9:10',3,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(3,'Heartless','9:10',3,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(4,'Legend','9:10',4,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(4,'Energy','9:10',4,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(4,'Madonna','9:10',4,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(4,'Star67','9:10',4,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(4,'Preach','9:10',4,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(5,'STARGAZING','9:10',5,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(5,'SICKO MODE','9:10',5,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(5,'WAKE UP','9:10',5,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(5,'ASTROTHUNDER','9:10',5,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(5,'YOSEMITE','9:10',5,'Hip Hop',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(6,'Cecilia Ann','9:10',6,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(6,'Rock Music','9:10',6,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(6,'Dig for Fire','9:10',6,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(6,'Havalina','9:10',6,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(6,'Blown Away','9:10',6,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(7,'Smells Like Teen Spirit','9:10',7,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(7,'Come As You Are','9:10',7,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(7,'Drain You','9:10',7,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(7,'Stay Away','9:10',7,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(7,'Endless, Nameless','9:10',8,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(8,'Speak to Me','9:10',9,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(8,'Time','9:10',9,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(8,'Brain Damage','9:10',9,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(8,'Eclipse','9:10',9,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(8,'Us and Them','9:10',9,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(9,'Tubular Bells (Pt. I)','9:10',10,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(9,'Tubular Bells (Pt. II)','9:10',10,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(9,'Mike Oldfield s Single','9:10',10,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(9,'Sailor s Hornpipe','9:10',10,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(10,'Airbag','9:10',11,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(10,'Fitter Happier','9:10',11,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(10,'The Tourist','9:10',11,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(10,'Paranoid Android','9:10',11,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(10,'No Surprises','9:10',11,'Rock',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(11,'Why So Serious','9:10',12,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(11,'A Dark Knight','9:10',12,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(11,'I Am Batman','9:10',12,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(11,'I m Not a Hero','9:10',12,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(12,'A Mandalorian and a Jedi','9:10',13,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(12,'Ahsoka Lives','9:10',13,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(12,'Capture the Flag','9:10',13,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(12,'A Friend','9:10',13,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(13,'Star Wars (Main Theme)','9:10',14,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(13,'The Imperial March (Darth Vaders Theme)','9:10',14,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(13,'The Battle in the Snow','9:10',14,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(13,'The Duel','9:10',14,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(14,'Prologue','9:10',15,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(14,'Harry s Wondrous World','9:10',15,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(14,'Christmas at Hogwarts','9:10',15,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(14,'Hedwig s Theme','9:10',15,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(15,'Far From Home Suite Home','9:10',16,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(15,'Multiple Realities','9:10',16,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(15,'The Magical Mysterio Tour','9:10',16,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(15,'Bridge and Love s Burning','9:10',16,'Movie Soundtracks',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(16,'Don t Know Why','9:10',17,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(16,'Seven Years','9:10',17,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(16,'Cold Cold Heart','9:10',17,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(16,'Feelin The Same Way','9:10',17,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(17,'What A Wonderful World (Single Version)','9:10',18,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(17,'Cabaret (Single Version)','9:10',18,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(17,'The Home Fire','9:10',18,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(17,'Dream A Little Dream Of Me','9:10',18,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(17,'Give Me Your Kisses','9:10',18,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(18,'Strange Meadow Lark','9:10',19,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(18,'Take Five','9:10',19,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(18,'Three to Get Ready','9:10',19,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(18,'Pick up Sticks','9:10',19,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(18,'Koto Song','9:10',19,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(19,'Desperado','9:10',20,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(19,'Superstar','9:10',20,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(19,'Feels Like Home','9:10',20,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(19,'In My Life','9:10',20,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(19,'Yeh Yeh','9:10',20,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(19,'Sorry Seems To Be The Hardest Word','9:10',20,'Jazz',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(20,'Departure','9:10',21,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(20,'Chilly','9:10',21,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(20,'Tokyo','9:10',21,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(20,'Maggie','9:10',21,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(21,'You Say','9:10',22,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(21,'Someone To You','9:10',22,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(21,'F?r Elise Jam','9:10',22,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(21,'More Than a Feeling / Long Time','9:10',22,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(22,'The Captain s Daughter','9:10',23,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(22,'Anne Louise','9:10',23,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(22,'Bones in the Ocean','9:10',23,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(22,'Retirement Song','9:10',23,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(23,'Ocean Motion','9:10',24,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(23,'Fall Festival','9:10',24,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(23,'Flight of the Humble Bee','9:10',24,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(23,'Tinker Bell s Dream','9:10',24,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(24,'Above and Beyond','9:10',25,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(24,'Tree of Life','9:10',25,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(24,'An Unfinished Life','9:10',25,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0),
(24,'Breaking Through','9:10',25,'Classical',5.00,10.00,15.00,'2020-01-01',false,0,0,0);


insert into Artists_to_tracks(artist_id,track_id) values
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(2,6),
(2,7),
(2,8),
(2,9),
(2,10),
(3,11),
(3,12),
(3,13),
(3,14),
(3,15),
(4,16),
(4,17),
(4,18),
(4,19),
(4,20),
(5,21),
(5,22),
(5,23),
(5,24),
(5,25),
(6,26),
(6,27),
(6,28),
(6,29),
(6,30),
(7,31),
(7,32),
(7,33),
(7,34),
(7,35),
(8,36),
(8,37),
(8,38),
(8,39),
(8,40),
(9,41),
(9,42),
(9,43),
(9,44),
(10,45),
(10,46),
(10,47),
(10,48),
(10,49),
(11,50),
(11,51),
(11,52),
(11,53),
(12,50),
(12,51),
(12,52),
(12,53),
(13,54),
(13,55),
(13,56),
(13,57),
(14,58),
(14,59),
(14,60),
(14,61),
(15,62),
(15,63),
(15,64),
(15,65),
(16,66),
(16,67),
(16,68),
(16,69),
(17,70),
(17,71),
(17,72),
(17,73),
(18,74),
(18,75),
(18,76),
(18,77),
(18,78),
(19,79),
(19,80),
(19,81),
(19,82),
(19,83),
(20,84),
(20,85),
(20,86),
(20,87),
(20,88),
(20,89),
(21,90),
(21,91),
(21,92),
(21,93),
(22,94),
(22,95),
(22,96),
(22,97),
(23,98),
(23,99),
(23,100),
(23,101),
(24,102),
(24,103),
(24,104),
(24,105),
(25,106),
(25,107),
(25,108),
(25,109);

insert into genre_to_tracks (track_id,genre_id) values 
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(6,1),
(7,1),
(8,1),
(9,1),
(10,1),
(11,1),
(12,1),
(13,1),
(14,1),
(15,1),
(16,1),
(17,1),
(18,1),
(19,1),
(20,1),
(21,1),
(22,1),
(23,1),
(24,1),
(25,1),
(26,2),
(27,2),
(28,2),
(29,2),
(30,2),
(31,2),
(32,2),
(33,2),
(34,2),
(35,2),
(36,2),
(37,2),
(38,2),
(39,2),
(40,2),
(41,2),
(42,2),
(43,2),
(44,2),
(45,2),
(46,2),
(47,2),
(48,2),
(49,2),
(50,3),
(51,3),
(52,3),
(53,3),
(54,3),
(55,3),
(56,3),
(57,3),
(58,3),
(59,3),
(60,3),
(61,3),
(62,3),
(63,3),
(64,3),
(65,3),
(66,3),
(67,3),
(68,3),
(69,3),
(70,4),
(71,4),
(72,4),
(73,4),
(74,4),
(75,4),
(76,4),
(77,4),
(78,4),
(79,4),
(80,4),
(81,4),
(82,4),
(83,4),
(84,4),
(85,4),
(86,4),
(87,4),
(88,4),
(89,4),
(90,5),
(91,5),
(92,5),
(93,5),
(94,5),
(95,5),
(96,5),
(97,5),
(98,5),
(99,5),
(100,5),
(101,5),
(102,5),
(103,5),
(104,5),
(105,5),
(106,5),
(107,5),
(108,5),
(109,5);

insert into genre_to_album (album_number,genre_id) values 
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(6,2),
(7,2),
(8,2),
(9,2),
(10,2),
(11,3),
(12,3),
(13,3),
(14,3),
(15,3),
(16,4),
(17,4),
(18,4),
(19,4),
(20,5),
(21,5),
(22,5),
(23,5),
(24,5)
;

insert into surveys(title) values
('How many movies have you watched in your life?'),
('How many cars have you built?'),
('What is your favorite color?'),
('How tall do you think the average height of creators of this site are?'),
('What is your favorite film?'),
('What is your spirit animal?'),
('What is the best season of Legend of Korra?');

insert into Choices(choice_name,votes) values
/* survey#1 */
('0-25',0),
('25-100',0),
('101-500',0),
('501-1000',0),
('1000 or more ',0),

/* survey#2 */
('None',0),
('1',0),
('2',0),
('300',0),
('more than 300',0),

/* survey#3 */
('Blue',0),
('Red',0),
('Purple',0),
('Other',0),

/* survey#4 */
('1 foot',0),
('4 foot',0),
('5 foot',0),
('12 foot',0),

/* survey#5 */
('The Dark Knight',0),
('Shawshank Redemption',0),
('Spider-man: Into The Spider-verse',0),
('Godfather',0),
('Other (The wrong answer)',0),

/* survey#6 */
('Wolf',0),
('Moose',0),
('Bear',0),
('Mongoose',0),

/* survey#7 */
('Season 1',0),
('Season 2',0),
('Season 3',0),
('Season 4',0);

insert into Survey_to_Choice(survey_id,choice_id) values

/* survey#1 */
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),

/* survey#2 */
(2,6),
(2,7),
(2,8),
(2,9),
(2,10),

/* survey#3 */
(3,11),
(3,12),
(3,13),
(3,14),

/* survey#4 */
(4,15),
(4,16),
(4,17),
(4,18),

/* survey#5 */
(5,19),
(5,20),
(5,21),
(5,22),
(5,23),

/* survey#6 */
(6,24),
(6,25),
(6,26),
(6,27),

/* survey#7 */
(7,28),
(7,29),
(7,30),
(7,31);

insert into Clients (client_number, title, last_name, first_name, company_name, address1, address2, city, province, country, postal_code, home_phone, cell_phone, email, username, password) values 
(1, 'Manager', 'Collingridge', 'Morton', 'DabZ', '8132 Lyons Plaza', '45059 Dottie Circle', 'Donnacona', 'Manitoba', 'Canada', 'G3M 3G5', '(546)267-4199', '(762)159-2854', 'cst.receive@gmail.com', 'DawsonManager', 'collegedawson'),
(2, 'Consumer', 'Riggey', 'Phillis', 'Dabvine', '12 Pankratz Avenue', '1796 Coleman Lane', 'Deep River', 'Alberta', 'Canada', 'M4J 4T3', '(974)586-1706', '(579)113-6429', 'cst.send@gmail.com', 'DawsonConsumer', 'dawsoncollege'),
(3, 'Consumer', 'Widdop', 'Zeke', 'Zoovu', '61 Veith Street', '738 Schurz Hill', 'Dieppe', 'New Brunswick', 'Canada', 'E1A 4S3', '(879)573-6174', '(264)385-8093', 'zwiddop2@mlb.com', 'zwiddop2', 'UErBWw'),
(4, 'Consumer', 'Disdel', 'Elysia', 'Livefish', '02 Holy Cross Pass', '94 Sutherland Center', 'Clarence-Rockland', 'Ontario', 'Canada', 'K4K F5S', '(455)378-4970', '(202)239-3454', 'edisdel3@tripod.com', 'edisdel3', 'y8u9GMfhyNU'),
(5, 'Consumer', 'Brokenbrow', 'Farrel', 'Jabberbean', '09161 Mitchell Pass', '761 Dexter Avenue', 'Saskatoon', 'Quebec', 'Canada', 'S7W 3G4', '(112)372-3925', '(963)314-2333', 'fbrokenbrow4@mozilla.com', 'fbrokenbrow4', 'ZhvTGcWID'),
(6, 'Consumer', 'Charlot', 'Kalina', 'Podcat', '94 Iowa Park', '988 Weeping Birch Junction', 'Papineauville', 'Yukon', 'Canada', 'G5N 5S3', '(972)674-8773', '(768)500-1272', 'kcharlot5@webmd.com', 'kcharlot5', '5Gq5J1'),
(7, 'Consumer', 'Dilon', 'Mab', 'Trudoo', '8 Artisan Plaza', '98384 Alpine Drive', 'Pincher Creek', 'New Brunswick', 'Canada', 'J0J 6J6', '(974)585-9826', '(236)286-7175', 'mdilon6@delicious.com', 'mdilon6', 'pXRGzPE'),
(8, 'Consumer', 'Speariett', 'Petronilla', 'Vidoo', '0208 Cardinal Road', '95 Stang Pass', 'Fox Creek', 'Nova Scotia', 'Canada', 'E4B 3F4', '(143)234-5437', '(605)876-1228', 'pspeariett7@g.co', 'pspeariett7', 'F9lBbU8Z'),
(9, 'Consumer', 'La Rosa', 'Major', 'Eazzy', '8 Lighthouse Bay Center', '58266 Monterey Crossing', 'St. Thomas', 'Manitoba', 'Canada', 'N5R 5S2', '(140)986-4159', '(838)357-4253', 'mlarosa8@stanford.edu', 'mlarosa8', '2lYiNfaEIn'),
(10, 'Consumer', 'MacKay', 'Kristo', 'Divanoodle', '21 Kingsford Terrace', '28024 Sunbrook Place', 'Sherwood Park', 'Prince Edward Island', 'Canada', 'T8A 7J3', '(544)584-8160', '(839)675-3263', 'kmackay9@harvard.edu', 'kmackay9', 'WPgoKZW0cJh');


/* website for pst gst and hst https://www.retailcouncil.org/resources/quick-facts/sales-tax-rates-by-province/*/
insert into provinces (province_id,choice_name,pst,gst,hst) values
(1,'Alberta',5,5,0),
(2,'British Columbia',7,5,0),
(3,'Manitoba',7,5,0),
(4,'New Brunswick',0,0,15),
(5,'Newfoundland and Labrador',0,0,15),
(6,'Northwest Territories',0,5,0),
(7,'Nova Scotia',0,0,15),
(8,'Nunavut',0,5,0),
(9,'Ontario',0,0,13),
(10,'Prince Edward',0,0,15),
(11,'Quebec',9.975,5,0),
(12,'Saskatchewan',6,5,0),
(13,'Yukon',0,5,5);

insert into Ads (ad_id,file_name,link) values
(1,'Anime','https://www.crunchyroll.com/'),
(2,'Beans','https://en.wikipedia.org/wiki/Bean'),
(3,'Cats','https://www.petfinder.com/pet-adoption/cat-adoption/'),
(4,'Mobile games','https://www.apple.com/app-store/'),
(5,'iPhone','https://www.apple.com/iphone-12/'),
(6,'Cheese','https://cheese.com/'),
(7,'Japan','https://en.wikipedia.org/wiki/Japan'),
(8,'Aurora','https://en.wikipedia.org/wiki/Aurora'),
(9,'Dogs','https://www.petfinder.com/pet-adoption/dog-adoption/'),
(10,'Online classes','https://zoom.us/');
