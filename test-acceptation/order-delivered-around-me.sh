#!/bin/bash

echo "As Jamie, I want to know the orders that will have to be delivered around me, so that I can choose one and go to the restaurant to begin the course."

echo "Erin: I choose ramen soup from soup restaurant"
echo `curl -o -I -L -s -w "%{http_code}" -d '{"name": "Erin","restaurant": "soup restaurant","product": "ramen soup","location": "49.8987,78.4235","phone": "0666666666"}' -H "Content-Type: application/json" -X POST http://localhost:8083/orders/new_order`
echo

echo "Gail: I choose ramen soup from soup restaurant"
echo `curl -o -I -L -s -w "%{http_code}" -d '{"name": "Gail","restaurant": "soup restaurant","product": "ramen soup","location": "60.8987,88.4235","phone": "0676666666"}' -H "Content-Type: application/json" -X POST http://localhost:8083/orders/new_order`
echo

echo "Jamie: I access to the order list that will have to be delivered around me (i.e coordinate 0.0,0.0)"
echo `curl -s -X GET http://localhost:8082/deliveries/rest/deliveries/deliveries/0/0`
echo


