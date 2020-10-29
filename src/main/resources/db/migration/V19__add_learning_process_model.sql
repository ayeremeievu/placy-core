create table core.learningprocesses (
    pk int NOT NULL GENERATED ALWAYS AS IDENTITY,
    createdAt timestamp not null,
    updatedAt timestamp not null,
    lp_city_pk int not null,
    lp_startDate timestamp,
    lp_finishDate timestamp,
    lp_status varchar(255) not null,
    PRIMARY KEY (pk),
    constraint fk_lp_city_pk
        foreign key (lp_city_pk)
            references core.cities (c_id)
);