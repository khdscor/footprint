alter table article_like
    add constraint article_like_article_id_member_id_unique unique (article_id, member_id);