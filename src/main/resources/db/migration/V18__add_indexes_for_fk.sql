create index c_division_id_idx on core.cities (c_division_id);
create index d_country_id_idx on core.divisions (d_country_id);
create index a_city_id_idx on core.addresses (a_city_id);

create index p_address_pk_idx on core.places (p_address_pk);

create index r_user_pk_idx on core.reviews (r_user_pk);
create index r_place_pk_idx on core.reviews (r_place_pk);

create index ypr_yelp_import_pk_idx on core.yelpplacesraw (ypr_yelp_import_pk);
create index yrr_yelp_import_pk_idx on core.yelpreviewsraw (yrr_yelp_import_pk);
create index yur_yelp_import_pk_idx on core.yelpusersraw (yur_yelp_import_pk);