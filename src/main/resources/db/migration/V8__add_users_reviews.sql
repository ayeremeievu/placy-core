create EXTENSION IF NOT EXISTS "uuid-ossp";

create table core.origins (
    pk varchar(255) not null,
    createdAt timestamp not null,
    updatedAt timestamp not null,
    o_code varchar(255),
    primary key (pk)
);

insert into core.origins
values (uuid_generate_v4(), current_timestamp, current_timestamp, 'placy'),
    (uuid_generate_v4(), current_timestamp, current_timestamp, 'yelp');

create table core.users (
    pk varchar(255) not null,
    createdAt timestamp not null,
    updatedAt timestamp not null,
    u_name varchar(255),
    u_lastName varchar(255),
    u_origin_pk varchar(255) not null,
    u_originCode varchar(255),
    primary key (pk),
    constraint fk_u_origin_pk
        foreign key (u_origin_pk)
            references core.origins (pk)
);

create table core.addresses (
    pk varchar(255) not null,
    createdAt timestamp not null,
    updatedAt timestamp not null,
    a_addressLine varchar(255) not null,
    a_city_id int not null,
    a_postalCode varchar(255) not null,
    primary key (pk),
    constraint fk_a_city_id
        foreign key (a_city_id)
            references core.cities (c_id)
);

create table core.places (
    pk varchar(255) not null,
    createdAt timestamp not null,
    updatedAt timestamp not null,
    p_name varchar(255) not null,
    p_city_id int not null,
    p_latitude double precision not null,
    p_longitude double precision not null,
    p_address_pk varchar(255) not null,
    p_description varchar(255),
    p_origin_pk varchar(255) not null,
    p_originCode varchar(255),
    primary key (pk),
    constraint fk_p_origin_pk
        foreign key (p_origin_pk)
            references core.origins (pk),
    constraint fk_p_address_pk
        foreign key (p_address_pk)
            references core.addresses (pk)
);

create table core.reviews (
    pk varchar(255) not null,
    createdAt timestamp not null,
    updatedAt timestamp not null,
    r_user_pk varchar(255) not null,
    r_origin_pk varchar(255) not null,
    r_originCode varchar(255),
    r_summary varchar(255) not null,
    r_place_pk varchar(255) not null,
    r_rate double precision not null,
    primary key (pk),
    constraint fk_r_user_pk
        foreign key (r_user_pk)
            references core.users (pk),
    constraint fk_r_origin_pk
        foreign key (r_origin_pk)
            references core.origins (pk),
    constraint fk_r_place_pk
        foreign key (r_place_pk)
            references core.places (pk)
);

create table core.categories (
    pk varchar(255) not null,
    createdAt timestamp not null,
    updatedAt timestamp not null,
    c_code varchar(255) not null,
    c_name varchar(255),
    primary key (pk)
);

insert into core.categories
values (uuid_generate_v4(), current_timestamp, current_timestamp, 'restaurants', 'Restaurants');

create table core.categoryToPlacesRel (
    ctp_place_pk varchar(255) not null,
    ctp_category_pk varchar(255) not null,
    primary key (ctp_place_pk, ctp_category_pk),
    constraint fk_ctp_place_pk
        foreign key (ctp_place_pk)
            references core.places (pk),
    constraint fk_ctp_category_pk
        foreign key (ctp_category_pk)
            references core.categories (pk)
);