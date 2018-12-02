create table user_list(
                        id serial primary key not null,
                        full_name varchar(60) not null,
                        e_mail varchar(120),
                        password varchar(15)
);

create table account (
                       id serial primary key not null,
                       user_list_id int not null references user(id),
                       balance money not null
);

create table transaction (
                           id serial primary key not null,
                           account_id int not null references account(id),
                           sum money not null,
                           date date not null,
                           categorie_id int not null references categorie(id)
);

create table categorie (
                         id serial primary key not null,
                         description varchar(25) not null
);
