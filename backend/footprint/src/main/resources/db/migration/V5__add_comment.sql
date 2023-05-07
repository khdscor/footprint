create table comment (
    id bigint not null auto_increment,
    content longtext,
    create_date datetime(6),
    article_id bigint not null,
    member_id bigint not null,
    primary key (id)
);