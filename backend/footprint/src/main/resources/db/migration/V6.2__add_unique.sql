alter table comment_like
    add constraint comment_like_comment_id_member_id_unique unique (comment_id, member_id);