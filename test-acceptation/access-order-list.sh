#!/bin/bash

echo "As Jordan, I want to access to the order list, so that I can prepare the meal efficiently."

echo "Erin: I choose ramen soup from soup restaurant"
echo `curl -o -I -L -s -w "%{http_code}" -d '{"name": "Erin","restaurant": "soup restaurant","product": "ramen soup", "customerLocation": "60.8987,88.4235", "restaurantLocation": "60.8987,88.4235","phone": "0666666666"}' -H "Content-Type: application/json" -X POST http://localhost:8083/orders/new_order`
echo

sleep 1

echo "Jordan: I access to the order list of my restaurant: the soup restaurant"
echo `curl -X GET http://localhost:8083/orders/restaurants/soup%20restaurant/orders`
echo


