alter table article_group
add constraint article_group_article_id_foreign_key
foreign key (article_id)
references article (id) on delete cascade;

alter table article_group
add constraint article_group_group_id_foreign_key
foreign key (group_id)
references group_table (id) on delete cascade;