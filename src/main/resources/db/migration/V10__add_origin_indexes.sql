CREATE INDEX u_origin_pk_origin_code_idx
    ON core.users (u_origin_pk, u_originCode);

CREATE INDEX p_origin_pk_origin_code_idx
    ON core.places (p_origin_pk, p_originCode);

CREATE INDEX r_origin_pk_origin_code_idx
    ON core.reviews (r_origin_pk, r_originCode);