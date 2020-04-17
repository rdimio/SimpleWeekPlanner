drop table if exists days cascade;
create table days
(id bigserial, date date , primary key(id));
insert into days(date) values ('16.04.2020');

drop table if exists day_targets cascade;
create table day_targets
(id bigserial, title varchar(255), priority integer,
creation varchar(1), time time, days_id bigint, primary key(id),
foreign key (days_id) references  days (id));
insert into day_targets (title, priority, creation, time, days_id) values
('позвонить маме',4,'д','00:15:00',1),
('запрогать аутентификацию',4,'и','02:00:00',1),
('проверить почту',2,'с','00:20:00',1);

drop table if exists five_y_targets cascade;
create table five_y_targets
(id bigserial primary key, title varchar(255));

drop table if exists three_y_targets cascade;
create table three_y_targets
(id bigserial primary key, title varchar(255));

drop table if exists year_targets cascade;
create table year_targets
(id bigserial primary key, title varchar(255));

drop table if exists month_targets cascade;
create table month_targets
(id bigserial primary key, title varchar(255));

drop table if exists week_targets cascade;
create table week_targets
(id bigserial primary key, title varchar(255));
