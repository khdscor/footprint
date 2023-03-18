create table user (
    id bigint not null auto_increment,
    email varchar(100) not null,
    image_url varchar(255),
    join_date datetime(6),
    nick_name varchar(100) not null,
    password varchar(255),
    provider varchar(255),
    provider_id varchar(255),
    role varchar(20) not null,
    primary key (id)
);