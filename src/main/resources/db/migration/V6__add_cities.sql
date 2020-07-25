-- ----------------------------
-- Table structure for cities
-- ----------------------------
DROP TABLE IF EXISTS core.cities;
CREATE TABLE core.cities
(
    c_id          int          NOT NULL GENERATED ALWAYS AS IDENTITY,
    c_city_name   varchar(255) NOT NULL,
    c_division_id int          NOT NULL,
    PRIMARY KEY (c_id),
    CONSTRAINT fr_division_code_country_id
        FOREIGN KEY (c_division_id)
            REFERENCES core.division (d_id)
);
