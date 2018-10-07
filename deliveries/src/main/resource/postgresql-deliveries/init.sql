CREATE TABLE deliveries (
    commandId integer PRIMARY KEY,
    deliveryManId integer,
    addressClient varchar(50)
);

CREATE TABLE openjpa_sequence_table (
  ID integer PRIMARY KEY,
  SEQUENCE_VALUE bigint
);


