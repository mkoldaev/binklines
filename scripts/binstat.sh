#!/bin/sh

JAVANAME=binanceklines$1
INTERVAL=$1
PARASUPDATE="PARASUPDATE"
#java -Dname=$JAVANAME -jar /scripts/binklines.jar $INTERVAL >> /scripts/$INTERVAL.log
#/scripts/binstat.sh 15m
#/scripts/binstat.sh 155m EOSUSDT PARASUPDATE

if [ "$3" ]
then
   echo "$PARASUPDATE is not running. Begin runing with updating paras"
   java -Dname=$JAVANAME -jar /scripts/binklines.jar $INTERVAL $2 $3 >> /scripts/$PARASUPDATE.log
   exit
fi

if ps ax | grep -v grep | grep $PARASUPDATE > /dev/null
then
    echo "Now updating paras. Exit"
    exit
fi

if ps ax | grep -v grep | grep $JAVANAME > /dev/null
then
    echo "$JAVANAME with $INTERVAL running. Exit."
else
    echo "$JAVANAMR is not running. Begin runing with $INTERVAL"
    java -Dname=$JAVANAME -jar /scripts/binklines.jar $INTERVAL >> /scripts/$INTERVAL.log
fi
