CREATE TABLE orders (
    orderId integer PRIMARY KEY,
    productId integer,
    restaurantId integer
);

CREATE TABLE openjpa_sequence_table (
  ID integer PRIMARY KEY,
  SEQUENCE_VALUE bigint
);
