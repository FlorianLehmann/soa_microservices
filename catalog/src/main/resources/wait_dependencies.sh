#!/bin/bash

sleep 5
while ! nc -z postgresql-catalog 5432; do sleep 5; done
catalina.sh run