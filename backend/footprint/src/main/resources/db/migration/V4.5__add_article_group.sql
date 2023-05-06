create table article_group (
    id bigint not null auto_increment,
    create_date datetime(6),
    article_id bigint not null,
    group_id bigint not null,
primary key (id)
);

alter table article_group
    add constraint article_group_article_id_group_id_unique unique (article_id, group_id);