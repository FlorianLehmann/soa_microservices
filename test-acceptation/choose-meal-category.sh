#!/bin/bash

echo "As Gail, I can browse the food catalogue by categories so that I can immediately identify my favorite junk food"

echo "--> Get all categories of meals"
echo `curl -s -X GET http://localhost:8080/catalog/rest/catalog/categories`
echo


echo "--> Get all meals that are in soup category"
echo `curl -s -X GET http://localhost:8080/catalog/rest/catalog/categories/soup/meals`
echo