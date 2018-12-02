create table person(
                        id serial primary key not null,
                        full_name varchar(60) not null,
                        e_mail varchar(120),
                        password varchar(15)
);

create table account (
                       id serial primary key not null,
                       person_id int not null references person(id),
                       balance money not null,
                       currency_id int not null references currency(id),
                       description_id int not null references account_description(id)
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

create table currency (
                       id serial primary key not null,
                       name_of_currency varchar(20) not null
);

create table account_description (
                      id serial primary key not null,
                      description varchar(50) not null
);

                      create index name_index
                      on person (full_name);

                      create index sum
                      on transaction (sum);

alter table account
                       add person_id int not null references person(id),
                       add currency_id int not null references currency(id),
                       add description_id int not null references account_description(id),
                       drop  user_list_id;