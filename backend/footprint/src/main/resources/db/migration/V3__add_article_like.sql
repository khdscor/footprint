create table article_like (
    id bigint not null auto_increment,
    article_id bigint not null,
    member_id bigint not null,
    primary key (id)
);