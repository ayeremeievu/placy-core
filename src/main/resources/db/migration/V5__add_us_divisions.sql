-- ----------------------------
-- Table structure for states
-- ----------------------------
DROP TABLE IF EXISTS core.division;
CREATE TABLE core.division
(
    d_id         int          NOT NULL GENERATED ALWAYS AS IDENTITY,
    d_name       varchar(255) NOT NULL,
    d_code       varchar(255) NOT NULL,
    d_country_id INT          NOT NULL,
    PRIMARY KEY (d_id),
    CONSTRAINT fk_d_country_id
        FOREIGN KEY (d_country_id)
            REFERENCES core.country (c_id)
);

CREATE INDEX d_name_idx
    ON core.division (d_id);

INSERT INTO core.division
VALUES (DEFAULT, 'Alaska', 'AK', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Alabama', 'AL', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Arkansas', 'AR', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Arizona', 'AZ', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'California', 'CA', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Colorado', 'CO', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Connecticut', 'CT', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'District of Columbia', 'DC', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Delaware', 'DE', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Florida', 'FL', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Georgia', 'GA', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Hawaii', 'HI', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Iowa', 'IA', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Idaho', 'ID', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Illinois', 'IL', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Indiana', 'IN', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Kansas', 'KS', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Kentucky', 'KY', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Louisiana', 'LA', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Massachusetts', 'MA', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Maryland', 'MD', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Maine', 'ME', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Michigan', 'MI', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Minnesota', 'MN', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Missouri', 'MO', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Mississippi', 'MS', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Montana', 'MT', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'North Carolina', 'NC', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'North Dakota', 'ND', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Nebraska', 'NE', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'New Hampshire', 'NH', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'New Jersey', 'NJ', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'New Mexico', 'NM', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Nevada', 'NV', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'New York', 'NY', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Ohio', 'OH', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Oklahoma', 'OK', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Oregon', 'OR', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Pennsylvania', 'PA', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Rhode Island', 'RI', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'South Carolina', 'SC', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'South Dakota', 'SD', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Tennessee', 'TN', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Texas', 'TX', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Utah', 'UT', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Virginia', 'VA', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Vermont', 'VT', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Washington', 'WA', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Wisconsin', 'WI', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'West Virginia', 'WV', 226);
INSERT INTO core.division
VALUES (DEFAULT, 'Wyoming', 'WY', 226);
