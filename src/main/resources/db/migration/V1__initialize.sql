DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id                    bigserial,
  password              VARCHAR(80),
  email                 VARCHAR(50) UNIQUE,
  login            VARCHAR(50),
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

INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users (password, login, email)
VALUES
('$2y$12$G3.H5B2vrDOfmrwl/he/8.IZ12zNXHqmRbm2FVoMiDdqkOHZCf8vW','admin','admin@gmail.com'),
('$2y$12$G3.H5B2vrDOfmrwl/he/8.IZ12zNXHqmRbm2FVoMiDdqkOHZCf8vW','user1','user1@gmail.com'),
('$2y$12$G3.H5B2vrDOfmrwl/he/8.IZ12zNXHqmRbm2FVoMiDdqkOHZCf8vW','user2','user2@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 1);

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
creation varchar(2), time varchar(5),  days_id bigint, user_id bigint, primary key(id),
foreign key (days_id) references  days (id),
foreign key (user_id) references  users (id));
/*insert into day_targets (title, priority, creation, time, days_id, user_id)
values ('бег 45 минут', 4, 'ф', '00:45', 1, 1),
('бег 45 минут', 4, 'ф', '00:45', 3, 1),
('бег 45 минут', 4, 'ф', '00:45', 5, 1),
('работа над проектом', 4, 'и', '02:00', 2, 1),
('сгонять на шашлыки', 1, 'с', '04:15', 5, 1),
('позвонить по поводу интернета', 2, 'с', '00:10', 4, 1),
('провести собеседование', 3, 'с', '01:0', 3, 1);*/