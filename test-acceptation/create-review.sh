#!/bin/bash

echo "As Jordan, I want the customers to be able to review the meals so that I can improve them according to their feedback."

echo "Erin: I post a review"
echo `curl -o -I -L -s -w "%{http_code}" -d '{"reviewer": "Erin", "restaurant": "soup restaurant", "meal": "ramen soup", "feedback": "This ramen soup is legen... wait for it... dary"}' -H "Content-Type: application/json" -X POST http://localhost:8080/catalog/rest/catalog/reviews`
echo

echo "Jordan: I access to the reviews of my restaurants"
echo `curl -X GET http://localhost:8080/catalog/rest/catalog/reviews/restaurants/soup%20restaurant`
echo

echo "Jordan: I access to the reviews of my products"
echo `curl -X GET http://localhost:8080/catalog/rest/catalog/reviews/restaurants/soup%20restaurant/meals/ramen%20soup`
echo
