--run each one by one in MySQL Workbench, after we create a schema named user_db
use user_db

CREATE TABLE User
(
    USERNAME varchar(255),
    PASSWORD varchar(255)
);

select * from user

    insert into user_db.User(USERNAME, PASSWORD) values ("Luciebest1", "Password1!")
insert into user_db.User(USERNAME, PASSWORD) values ("Luciebest2", "Password2!")

select * from user