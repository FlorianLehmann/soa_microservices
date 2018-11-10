#!/bin/bash

echo "As Jordan, I want to access to the order list, so that I can prepare the meal efficiently."

echo "Erin: I choose ramen soup from soup restaurant"
echo `curl -o -I -L -s -w "%{http_code}" -d '{"name": "Erin","restaurant": "soup restaurant","product": "ramen soup","location": "49.8987,78.4235","phone": "0666666666"}' -H "Content-Type: application/json" -X POST http://localhost:8083/orders/new_order`
echo

echo "Jordan: I access to the order list of my restaurant: the soup restaurant"
echo `curl -X GET http://localhost:8083/orders/restaurants/soup%20restaurant/orders`
echo


