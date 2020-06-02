DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id                    bigserial,
                       password              VARCHAR(80),
                       email                 VARCHAR(30) UNIQUE,
                       login                 VARCHAR(20),
                       token                 VARCHAR(80) DEFAULT null,
                       enabled               boolean DEFAULT false,
                       PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
                       id                    serial,
                       name                  VARCHAR(50) NOT NULL,
                       PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
                             user_id               INT NOT NULL,
                             role_id               INT NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY (user_id)
                                 REFERENCES users (id),
                             FOREIGN KEY (role_id)
                                 REFERENCES roles (id)
);

drop table if exists days cascade;
create table days
(id bigserial, title varchar(15), user_id bigint, primary key(id));
insert into days(title) values ('Понедельник'),('Вторник'),
                               ('Среда'),('Четверг'),('Пятница'),('Суббота'),('Воскресенье'),
                               ('Monday'),('Tuesday'),
                               ('Wednesday'),('Thursday'),('Friday'),('Saturday'),('Sunday');

drop table if exists day_targets cascade;
create table day_targets
(id bigserial, title varchar(255), priority integer,
 creation varchar(2), time time not null,  days_id bigint, user_id bigint, primary key(id),
 foreign key (days_id) references  days (id),
 foreign key (user_id) references  users (id));