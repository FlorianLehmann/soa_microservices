#!/bin/bash

echo "As Erin, I want to know before ordering the estimated time of delivery of the meal so that I can schedule my work around it, and be ready when it arrives"

echo "Get an ETA for a ramen soup at soup restaurant"
echo $'curl http://localhost:8084/kitchen/rest/kitchen/eta/soup%20restaurant/ramen%20soup'
echo
