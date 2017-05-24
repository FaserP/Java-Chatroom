#! /bin/sh
#/*
# Added to the Apache River distribution by
# Dr Gary Allen, University of Huddersfield
# to simplify starting the 'outrigger' implementation
# of the JavaSpace specification
#*/

# Shell script to run JRMP Outrigger

set -x

java -Djava.rmi.server.useCodebaseOnly=false \
     -Djava.security.policy=config/start.policy \
     -jar ../../lib/start.jar \
     config/start-outrigger-group.config
