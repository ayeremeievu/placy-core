-- ----------------------------
-- Table structure for states
-- ----------------------------
DROP TABLE IF EXISTS core.divisions;
CREATE TABLE core.divisions
(
    d_id         int          NOT NULL GENERATED ALWAYS AS IDENTITY,
    d_name       varchar(255) NOT NULL,
    d_code       varchar(255) NOT NULL,
    d_country_id INT          NOT NULL,
    PRIMARY KEY (d_id),
    CONSTRAINT fk_d_country_id
        FOREIGN KEY (d_country_id)
            REFERENCES core.countries (c_id)
);

CREATE INDEX d_name_idx
    ON core.divisions (d_id);

INSERT INTO core.divisions
VALUES (DEFAULT, 'Alaska', 'AK', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Alabama', 'AL', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Arkansas', 'AR', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Arizona', 'AZ', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'California', 'CA', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Colorado', 'CO', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Connecticut', 'CT', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'District of Columbia', 'DC', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Delaware', 'DE', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Florida', 'FL', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Georgia', 'GA', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Hawaii', 'HI', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Iowa', 'IA', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Idaho', 'ID', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Illinois', 'IL', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Indiana', 'IN', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Kansas', 'KS', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Kentucky', 'KY', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Louisiana', 'LA', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Massachusetts', 'MA', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Maryland', 'MD', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Maine', 'ME', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Michigan', 'MI', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Minnesota', 'MN', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Missouri', 'MO', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Mississippi', 'MS', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Montana', 'MT', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'North Carolina', 'NC', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'North Dakota', 'ND', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Nebraska', 'NE', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'New Hampshire', 'NH', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'New Jersey', 'NJ', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'New Mexico', 'NM', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Nevada', 'NV', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'New York', 'NY', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Ohio', 'OH', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Oklahoma', 'OK', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Oregon', 'OR', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Pennsylvania', 'PA', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Rhode Island', 'RI', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'South Carolina', 'SC', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'South Dakota', 'SD', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Tennessee', 'TN', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Texas', 'TX', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Utah', 'UT', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Virginia', 'VA', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Vermont', 'VT', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Washington', 'WA', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Wisconsin', 'WI', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'West Virginia', 'WV', 226);
INSERT INTO core.divisions
VALUES (DEFAULT, 'Wyoming', 'WY', 226);
