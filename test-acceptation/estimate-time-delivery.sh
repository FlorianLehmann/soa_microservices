#!/bin/bash

echo "As Erin, I want to know before ordering the estimated time of delivery of the meal so that I can schedule my work around it, and be ready when it arrives"

echo $'curl http://localhost:8084/kitchen/rest/kitchen/eta'

