#! /bin/sh
#/*
# Added to the Apache River distribution by
# Dr Gary Allen, University of Huddersfield
# to simplify starting the 'mahalo' implementation
# of the Transaction Manager
#*/

# Shell script to run JRMP Mahalo

set -x

java -Djava.rmi.server.useCodebaseOnly=false \
     -Djava.security.policy=config/start.policy \
     -jar ../../lib/start.jar \
     config/start-mahalo-group.config
