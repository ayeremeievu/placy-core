alter table core.users
    add column pk_int int;

alter table core.places
    add column pk_int int;

alter table core.reviews
    add column pk_int int;

CREATE SEQUENCE users_sequence;
UPDATE core.users SET pk_int = nextval('users_sequence');

CREATE SEQUENCE places_sequence;
UPDATE core.places SET pk_int = nextval('places_sequence');

CREATE SEQUENCE reviews_sequence;
UPDATE core.reviews SET pk_int = nextval('reviews_sequence');