alter table article
add constraint article_user_id_foreign_key
foreign key (user_id)
references user (id);