create table comment_like (
    id bigint not null auto_increment,
    comment_id bigint not null,
    member_id bigint not null,
    primary key (id)
);
