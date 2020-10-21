alter table core.reviews DROP COLUMN r_user_pk;
alter table core.reviews DROP COLUMN r_place_pk;
alter table core.categoryToPlacesRel DROP COLUMN ctp_place_pk;

alter table core.reviews
    rename COLUMN r_user_pk_int TO r_user_pk;
alter table core.reviews
    rename COLUMN r_place_pk_int TO r_place_pk;
alter table core.categoryToPlacesRel
    rename COLUMN ctp_place_pk_int TO ctp_place_pk;

alter table core.reviews
    add CONSTRAINT fk_r_user_pk FOREIGN KEY (r_user_pk) REFERENCES core.users (pk);
alter table core.reviews
    add CONSTRAINT fk_r_place_pk FOREIGN KEY (r_place_pk) REFERENCES core.places (pk);

alter table core.categoryToPlacesRel
    add CONSTRAINT fk_ctp_place_pk FOREIGN KEY (ctp_place_pk) REFERENCES core.places (pk);

