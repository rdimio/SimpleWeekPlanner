drop table if exists days cascade;
create table days
(id bigserial, title varchar(15) , primary key(id));
insert into days(title) values ('Понедельник'),('Вторник'),
('Среда'),('Четверг'),('Пятница'),('Суббота'),('Воскресенье');

drop table if exists day_targets cascade;
create table day_targets
(id bigserial, title varchar(255), priority integer,
creation varchar(1), time varchar(5), days_id bigint, primary key(id),
foreign key (days_id) references  days (id));

drop table if exists week_targets cascade;
create table week_targets
(id bigserial primary key, title varchar(255));
