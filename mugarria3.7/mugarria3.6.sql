create database mugarria3_6;
use  mugarria3_6;

create table Pictures (
pictureId int,
title varchar(255),
date date,
file varchar(255),
visits int,
photographerId int,
primary key(pictureId));

create table Photographers (
photographerId int,
name varchar(50),
awarded boolean,
primary key(photographerId));

alter table Pictures add foreign key(photographerId) references Photographers(photographerId) on update cascade on delete restrict;


INSERT INTO Photographers (photographerId, name, awarded)
VALUES (1, 'ansealdams', 0);


INSERT INTO Photographers (photographerId, name, awarded)
VALUES (2, 'rothko', 0);


INSERT INTO Photographers (photographerId, name, awarded)
VALUES (3, 'vangogh', 1);


INSERT INTO Pictures (pictureId, title, date, file, visits, photographerId)
VALUES (1, 'ansealdams1', '2023-01-01', 'ansealdams1.jpg', 100, 1);


INSERT INTO Pictures (pictureId, title, date, file, visits, photographerId)
VALUES (2, 'ansealdams2', '2023-02-02', 'ansealdams2.jpg', 150, 1);


INSERT INTO Pictures (pictureId, title, date, file, visits, photographerId)
VALUES (3, 'rothko1', '2023-03-03', 'rothko1.jpg', 200, 2);


INSERT INTO Pictures (pictureId, title, date, file, visits, photographerId)
VALUES (4, 'vangogh1', '2023-04-04', 'vangogh1.jpg', 300, 3);


INSERT INTO Pictures (pictureId, title, date, file, visits, photographerId)
VALUES (5, 'vangogh2', '2023-05-05', 'vangogh2.jpg', 250, 3);

/*drop table pictures;
drop table photographers;*/
