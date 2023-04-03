create database if not exists `myblog`;
use `myblog`;
create table if not exists user
(
    id bigint(19) not null primary key,
    number varchar(45) not null,
    password varchar(65) not null,
    nick_name varchar(65) not null,
    avatar_url varchar(200),
    sign varchar(200),
    insert_time datetime not null default current_timestamp,
    update_time datetime default current_timestamp,
    index (number),
    unique (number)
);