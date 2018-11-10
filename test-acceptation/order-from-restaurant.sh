#!/bin/bash

echo "As Gail or Erin, I can order my lunch from a restaurant so that the food is delivered to my place;"

echo "--> Get all restaurants"
echo `curl -s -X GET http://localhost:8080/catalog/rest/catalog/restaurants`
echo


echo "--> I want to visit soup restaurant"
echo `curl -s -X GET http://localhost:8080/catalog/rest/catalog/restaurants/soup%20restaurant/meals`
echo

echo "--> I decide to go for a  ramen soup"
echo `curl -s -X GET http://localhost:8080/catalog/rest/catalog/restaurants/soup%20restaurant/meals`
echo

echo "--> I validate my order"
echo `TODO`
echo

echo "--> Unfortunately, I have to pay my order"
echo `TODO`
echo

echo "--> "
echo `TODO`
echo "TO COMPLETE"