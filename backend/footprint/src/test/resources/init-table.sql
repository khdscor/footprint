drop table comment_like if exists;
drop table article_like if exists;
drop table article_group if exists;
drop table comment if exists;
drop table article if exists;
drop table member_group if exists;
drop table group_table if exists;
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

create table article_like (
    id bigint not null auto_increment,
    article_id bigint not null,
    member_id bigint not null,
    primary key (id)
);

create table group_table (
    id bigint not null auto_increment,
    create_date datetime(6),
    invitation_code varchar(255) unique,
    name varchar(255) not null,
    owner_id bigint not null,
    primary key (id)
);

create table member_group (
    id bigint not null auto_increment,
    create_date datetime(6),
    group_id bigint not null,
    member_id bigint not null,
    important boolean null,
    primary key (id)
);

create table article_group (
    id bigint not null auto_increment,
    create_date datetime(6),
    article_id bigint not null,
    group_id bigint not null,
primary key (id)
);

create table comment (
    id bigint not null auto_increment,
    content longtext,
    create_date datetime(6),
    article_id bigint not null,
    member_id bigint not null,
    primary key (id)
);

create table comment_like (
    id bigint not null auto_increment,
    comment_id bigint not null,
    member_id bigint not null,
    primary key (id)
);

alter table article_group
    add constraint article_group_article_id_group_id_unique unique (article_id, group_id);

alter table member_group
    add constraint member_group_member_id_group_id_unique unique (member_id, group_id);

alter table article
add constraint article_member_id_foreign_key
foreign key (member_id)
references member (id) on delete cascade;

alter table article_like
add constraint article_like_article_id_foreign_key
foreign key (article_id)
references article (id) on delete cascade;

alter table article_like
add constraint article_like_member_id_foreign_key
foreign key (member_id)
references member (id) on delete cascade;

alter table member_group
    add constraint member_group_group_id_foreign_key
        foreign key (group_id)
            references group_table (id) on delete cascade;

alter table member_group
    add constraint member_group_member_id_foreign_key
        foreign key (member_id)
            references member (id) on delete cascade;

alter table article_group
add constraint article_group_article_id_foreign_key
foreign key (article_id)
references article (id) on delete cascade;

alter table article_group
add constraint article_group_group_id_foreign_key
foreign key (group_id)
references group_table (id) on delete cascade;

alter table comment
add constraint comment_article_id_foreign_key
foreign key (article_id)
references article (id) on delete cascade;

alter table comment
add constraint comment_member_id_foreign_key
foreign key (member_id)
references member (id) on delete cascade;

alter table comment_like
add constraint comment_like_comment_id_foreign_key
foreign key (comment_id)
references comment (id) on delete cascade;

alter table comment_like
add constraint comment_like_member_id_foreign_key
foreign key (member_id)
references member (id) on delete cascade;

alter table comment_like
    add constraint comment_like_comment_id_member_id_unique unique (comment_id, member_id);