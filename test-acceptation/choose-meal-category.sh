#!/bin/bash

echo "As Gail, I can browse the food catalogue by categories so that I can immediately identify my favorite junk food"

echo "Jordan: I add the ramen soup from soup restaurant to the catalog"
echo `curl -o -I -L -s -w "%{http_code}" -d '{"category" : "soup", "restaurant": "soup restaurant", "name": "ramen soup"}' -H "Content-Type: application/json" -X POST http://localhost:8080/catalog/rest/catalog/meals`
echo

echo "--> Get all categories of meals"
echo `curl -s -X GET http://localhost:8080/catalog/rest/catalog/categories`
echo


echo "--> Get all meals that are in soup category"
echo `curl -s -X GET http://localhost:8080/catalog/rest/catalog/categories/soup/meals`
echo
