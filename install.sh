#!/bin/bash

# catalogue
cd catalog
mvn clean package -DskipTests=true
cd ..

# kitchen
cd kitchen
mvn clean package -DskipTests=true
cd ..

# deliveries
cd deliveries
mvn clean package -DskipTests=true
cd ..

# orders
cd orders
mvn clean package -DskipTests=true
cd ..


