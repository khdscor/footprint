alter table article
add constraint article_member_id_foreign_key
foreign key (member_id)
references member (id);