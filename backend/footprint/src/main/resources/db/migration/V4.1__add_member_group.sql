create table member_group (
    id bigint not null auto_increment,
    create_date datetime(6),
    group_id bigint not null,
    member_id bigint not null,
    important boolean null,
    primary key (id)
) engine=InnoDB;

alter table member_group
    add constraint member_group_member_id_group_id_unique unique (member_id, group_id);