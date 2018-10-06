#!/bin/bash

# catalogue
cd catalogue
mvn clean package
cd ..

# validation
cd validation
mvn clean package
cd ..

# deliveries
cd deliveries
mvn clean package
cd ..