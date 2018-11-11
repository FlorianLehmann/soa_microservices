#!/bin/bash

/bin/bash ./test-acceptation/access-order-list.sh
sleep 3

/bin/bash ./test-acceptation/choose-meal-category.sh
sleep 3

/bin/bash ./test-acceptation/create-review.sh
sleep 3

/bin/bash ./test-acceptation/estimate-time-delivery.sh
sleep 3

/bin/bash ./test-acceptation/order-delivered-around-me.sh
sleep 3



