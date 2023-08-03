create index public_map_index on article(public_map, latitude, longitude);

create index private_map_index on article(member_id, private_map, latitude, longitude);

create index group_map_index on article(id, latitude, longitude);
