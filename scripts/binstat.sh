#!/bin/sh

JAVANAME=binanceklines$1
INTERVAL=$1

if ps ax | grep -v grep | grep $JAVANAME > /dev/null
then
    echo "$JAVANAME with $INTERVAL running. Exit."
else
    echo "$JAVANAMR is not running. Begin runing with $INTERVAL"
    java -Dname=$JAVANAME -jar /scripts/binklines.jar $INTERVAL >> /scripts/$INTERVAL.log
fi
