#! /bin/sh
#/*
# Added to the Apache River distribution by
# Dr Gary Allen, University of Huddersfield
# to simplify all the services needed to run
# Javaspace programs.
# The script starts:
#     http server
#     regie  (lookup service)
#     outrigger  (javaspace)
#     mahalo  (transaction manager)
#*/

# Shell script to start all services needed 
# to run Javaspace applications

echo "\nStarting http server"
./scripts/httpd.sh &
sleep 3

echo "\n\nStarting regie  (lookup service)"
./scripts/jrmp-reggie.sh &
sleep 3

echo "\n\nStarting outrigger  (javaspace)"
./scripts/jrmp-outrigger-group.sh &
sleep 3

echo "\n\nStarting mahalo  (transaction manager)"
./scripts/jrmp-mahalo-group.sh &
sleep 3

echo "\nDone"

