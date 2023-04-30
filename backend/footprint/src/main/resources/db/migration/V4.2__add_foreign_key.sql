alter table member_group
add constraint member_group_group_id_foreign_key
foreign key (group_id)
references group_table (id) on delete cascade;

alter table member_group
add constraint member_group_member_id_foreign_key
foreign key (member_id)
references member (id) on delete cascade;
