alter table comment
add constraint comment_article_id_foreign_key
foreign key (article_id)
references article (id) on delete cascade;

alter table comment
add constraint comment_member_id_foreign_key
foreign key (member_id)
references member (id) on delete cascade;