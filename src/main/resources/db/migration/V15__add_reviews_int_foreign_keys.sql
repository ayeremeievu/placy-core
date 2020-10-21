alter table core.reviews ADD COLUMN r_user_pk_int int;
alter table core.reviews ADD COLUMN r_place_pk_int int;
alter table core.categoryToPlacesRel
    ADD COLUMN ctp_place_pk_int int;

update core.reviews as r
    set r_user_pk_int = (
        select u.pk_int
        from core.users as u
        where u.pk = r.r_user_pk
    );

update core.reviews as r
    set r_place_pk_int = (
        select p.pk_int
        from core.places as p
        where p.pk = r.r_place_pk
    );

update core.categoryToPlacesRel as ctp
    set ctp_place_pk_int = (
        select p.pk_int
        from core.places as p
        where p.pk = ctp.ctp_place_pk
    );