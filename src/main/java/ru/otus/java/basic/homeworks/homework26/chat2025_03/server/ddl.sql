drop table if exists users;
create table users(
id SERIAL primary key,
login varchar(255) not null unique,
password varchar(255) not null,
username varchar(255) not null unique
);

INSERT INTO public.users
(login, "password", username)
VALUES('admin', 'admin', 'admin');
INSERT INTO public.users
(login, "password", username)
VALUES('asd', 'asd', 'asd1');
INSERT INTO public.users
(login, "password", username)
VALUES('qwe', 'qwe', 'qwe1');
INSERT INTO public.users
(login, "password", username)
VALUES('root', 'root', 'root');


drop table if exists roles;
create table roles(
id SERIAL primary key,
name varchar(255) not null unique
);

INSERT INTO public.roles ("name") VALUES('admin');
INSERT INTO public.roles ("name") VALUES('user');
INSERT INTO public.roles ("name") VALUES('manager');



drop table if exists users_to_roles;
create table users_to_roles(
user_id int not null,
role_id int not null,
primary key (user_id, role_id),
foreign key (user_id) references users(id) on delete cascade,
foreign key (role_id) references roles(id) on delete cascade
);

INSERT INTO public.users_to_roles(user_id, role_id) VALUES(1, 1);
INSERT INTO public.users_to_roles(user_id, role_id) VALUES(2, 2);
INSERT INTO public.users_to_roles(user_id, role_id) VALUES(3, 2);
INSERT INTO public.users_to_roles(user_id, role_id) VALUES(4, 1);
