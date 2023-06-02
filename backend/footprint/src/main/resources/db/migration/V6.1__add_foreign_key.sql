alter table comment_like
add constraint comment_like_comment_id_foreign_key
foreign key (comment_id)
references comment (id) on delete cascade;

alter table comment_like
add constraint comment_like_member_id_foreign_key
foreign key (member_id)
references member (id) on delete cascade;
