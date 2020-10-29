ALTER TABLE core.users
    ADD COLUMN u_city_pk int;

ALTER TABLE core.users
    ADD CONSTRAINT u_city_pk_fk
        FOREIGN KEY (u_city_pk)
        REFERENCES core.cities(c_id);

create index u_city_pk_idx on core.users (u_city_pk);