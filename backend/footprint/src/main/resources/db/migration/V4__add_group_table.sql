create table group_table (
    id bigint not null auto_increment,
    create_date datetime(6),
    invitation_code varchar(255) unique,
    name varchar(255) not null,
    primary key (id)
);