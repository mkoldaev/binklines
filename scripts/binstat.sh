#!/bin/sh

JAVANAME=binanceklines$1
if [ "$2" ]; then JAVANAME="$JAVANAME$2"; fi
if [ "$3" ]; then JAVANAME="$JAVANAME$3"; fi
if [ "$4" ]; then JAVANAME="$JAVANAME$4"; fi

#java -Dname=$JAVANAME -jar /scripts/binklines.jar $INTERVAL >> /scripts/$INTERVAL.log
#/scripts/binstat.sh 15m
#/scripts/binstat.sh 155m EOSUSDT PARASUPDATE
#/scripts/binstat.sh 155m EOSUSDT PARASUPDATE CALC

if ps ax | grep -v grep | grep $JAVANAME > /dev/null
then
    echo "$JAVANAME running. Exit."
else
    echo "$JAVANAME is not running. Begin runing"
    java -Dname=$JAVANAME -jar /scripts/binklines.jar $1 $2 $3 $4 >> /scripts/$JAVANAME.log
fi