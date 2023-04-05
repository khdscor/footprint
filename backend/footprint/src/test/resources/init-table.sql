drop table article if exists;
drop table member if exists;


create table member (
    id bigint not null auto_increment,
    email varchar(100) not null,
    image_url varchar(255),
    join_date datetime(6),
    nick_name varchar(100) not null,
    password varchar(255),
    provider varchar(255),
    provider_id varchar(255),
    role varchar(20) not null,
    refresh_token varchar(255),
    primary key (id)
);

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

alter table article
add constraint article_member_id_foreign_key
foreign key (member_id)
references member (id);