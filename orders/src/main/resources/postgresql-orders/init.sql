CREATE TABLE orders (
    orderId integer PRIMARY KEY,
    name varchar(50),
    restaurant varchar(50),
    product varchar(50),
    location varchar(21),
    phone varchar(12)
);

CREATE TABLE openjpa_sequence_table (
  ID integer PRIMARY KEY,
  SEQUENCE_VALUE bigint
);
