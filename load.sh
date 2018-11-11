#!/usr/bin/env bash

curl -o -I -L -s -w "%{http_code}" -d '{"category" : "soup", "restaurant": "soup restaurant", "name": "ramen soup"}' -H "Content-Type: application/json" -X POST http://localhost:8080/catalog/rest/catalog/meals
cd test-charge
mvn -Dgatling.simulation.name=HttpSimulation1 clean gatling:execute
mvn -Dgatling.simulation.name=HttpSimulation2 clean gatling:execute
cd ..