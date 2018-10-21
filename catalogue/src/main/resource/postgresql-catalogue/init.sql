CREATE TABLE meals (
    id integer PRIMARY KEY,
    category varchar(50),
    restaurant varchar(50),
    name varchar(50)
);

CREATE TABLE openjpa_sequence_table (
  ID integer PRIMARY KEY,
  SEQUENCE_VALUE bigint
);

INSERT INTO meals VALUES (0, 'soup', 'soup restaurant', 'ramen soup');