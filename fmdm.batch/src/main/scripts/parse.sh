#!/bin/bash

export MONITOR_BASE=/opt/founder/monitor
export JAVA_HOME=/opt/founder/jdk1.7.0_04
LIB_DIR=$MONITOR_BASE/lib

cnt=`ps -ef | grep "com.founder.monitor.DataParseApp" | grep -v grep | wc -l`
if [ $cnt -gt 0 ]; then
    echo "已经运行了一个相同实例，请停止其它实例再试。"
    exit 1
fi

CP3=$(JARS=("$LIB_DIR"/*.jar); IFS=:; echo "${JARS[*]}")

CFG=$MONITOR_BASE/config

$JAVA_HOME/bin/java -cp "$CFG:$CP3" -DINSTANCE=parse com.founder.monitor.DataParseApp pro1
