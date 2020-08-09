CREATE TABLE core.yelpImports
(
    pk            INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    createdAt     TIMESTAMP    NOT NULL,
    updatedAt     TIMESTAMP    NOT NULL,
    yi_startDate  TIMESTAMP,
    yi_status     VARCHAR(255) NOT NULL,
    yi_finishDate TIMESTAMP,
    PRIMARY KEY (pk)
);

CREATE TABLE core.yelpPlacesRaw
(
    ypr_id             VARCHAR(255) NOT NULL,
    createdAt          TIMESTAMP NOT NULL,
    updatedAt          TIMESTAMP NOT NULL,
    ypr_name           VARCHAR(255),
    ypr_address        VARCHAR(255),
    ypr_city           VARCHAR(255),
    ypr_state          VARCHAR(255),
    ypr_postalCode     VARCHAR(255),
    ypr_latitude       DOUBLE PRECISION,
    ypr_longitude      DOUBLE PRECISION,
    ypr_stars          DOUBLE PRECISION,
    ypr_reviewCount    VARCHAR(255),
    ypr_yelp_import_pk INT       NOT NULL,
    PRIMARY KEY (ypr_id, ypr_yelp_import_pk),
    CONSTRAINT fk_ypr_yelp_import_pk
        FOREIGN KEY (ypr_yelp_import_pk)
            REFERENCES core.yelpImports (pk)
);

CREATE TABLE core.yelpReviewsRaw
(
    yrr_id             VARCHAR(255) NOT NULL,
    createdAt          TIMESTAMP NOT NULL,
    updatedAt          TIMESTAMP NOT NULL,
    yrr_userId         VARCHAR(255),
    yrr_businessId     VARCHAR(255),
    yrr_stars          DOUBLE PRECISION,
    yrr_yelp_import_pk INT NOT NULL,
    PRIMARY KEY (yrr_id, yrr_yelp_import_pk),
    CONSTRAINT fk_yrr_yelp_import_pk
        FOREIGN KEY (yrr_yelp_import_pk)
            REFERENCES core.yelpImports (pk)
);

CREATE TABLE core.yelpUsersRaw
(
    yur_id             VARCHAR(255) NOT NULL,
    createdAt          TIMESTAMP NOT NULL,
    updatedAt          TIMESTAMP NOT NULL,
    yur_name           VARCHAR(255),
    yur_reviewCount    INTEGER,
    yur_yelpingSince   VARCHAR(255),
    yur_yelp_import_pk INT NOT NULL,
    PRIMARY KEY (yur_id, yur_yelp_import_pk),
    CONSTRAINT fk_yur_yelp_import_pk
        FOREIGN KEY (yur_yelp_import_pk)
            REFERENCES core.yelpImports (pk)
);
