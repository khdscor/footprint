drop index group_map_index on footprintDB.article;

create index group_map_index on article(latitude, longitude, id);