#!/bin/bash

# catalog
cd catalog
mvn clean package
cd ..

# kitchen
cd kitchen
mvn clean package
cd ..

# deliveries
cd deliveries
mvn clean package
cd ..

# orders
cd orders
mvn clean package
cd ..
