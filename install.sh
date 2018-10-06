#!/bin/bash

# catalogue
cd catalogue
mvn clean package
cd ..

# validation
cd validation
mvn clean package
cd ..