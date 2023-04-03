drop table if exists article;

create table article (
    id bigint not null auto_increment,
    content longtext,
    create_date datetime(6),
    latitude double precision,
    longitude double precision,
    private_map bit not null,
    public_map bit not null,
    title varchar(50) not null,
    member_id bigint not null,
    primary key (id)
);