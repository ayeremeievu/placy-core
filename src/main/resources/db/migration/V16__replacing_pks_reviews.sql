drop sequence users_sequence;
drop sequence places_sequence;
drop sequence reviews_sequence;

alter table core.reviews drop CONSTRAINT fk_r_user_pk;
alter table core.reviews drop CONSTRAINT fk_r_place_pk;
alter table core.categoryToPlacesRel drop CONSTRAINT fk_ctp_place_pk;

alter table core.users drop CONSTRAINT users_pkey;
alter table core.places drop CONSTRAINT places_pkey;
alter table core.reviews drop CONSTRAINT reviews_pkey;

alter table core.users
   drop COLUMN pk;
alter table core.places
   drop COLUMN pk;
alter table core.reviews
   drop COLUMN pk;

alter table core.users
    rename COLUMN pk_int TO pk;
alter table core.places
    rename COLUMN pk_int TO pk;
alter table core.reviews
    rename COLUMN pk_int TO pk;

alter table core.users add PRIMARY KEY (pk);
alter table core.places add PRIMARY KEY (pk);
alter table core.reviews add PRIMARY KEY (pk);

alter table core.users
    alter pk set NOT NULL,
    alter pk ADD GENERATED ALWAYS AS IDENTITY;
alter table core.places
    alter pk set NOT NULL,
    alter pk ADD GENERATED ALWAYS AS IDENTITY;
alter table core.reviews
    alter pk set NOT NULL,
    alter pk ADD GENERATED ALWAYS AS IDENTITY;