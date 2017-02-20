#!/bin/bash

export MONITOR_BASE=/opt/founder/monitor
export JAVA_HOME=/opt/founder/jdk1.7.0_04
LIB_DIR=$MONITOR_BASE/lib

cnt=`ps -ef | grep "com.founder.monitor.MainApp pro1" | grep -v grep | wc -l`
if [ $cnt -gt 0 ]; then
    echo "已经运行了一个相同实例，请停止其它实例再试。"
    exit 1
fi

# 无法处理带空格的目录
CP0=$(echo "$LIB_DIR"/*.jar | tr ' ' ':')
# 依赖于GNU find, Mac OS X无法使用
CP1=$(find "$LIB_DIR" -name '*.jar' -printf '%p:' | sed 's/:$//')
# 可用于Mac OS X
CP2=$(find "$LIB_DIR" -name '*.jar' |xargs echo  |tr ' ' ':')
# 
CP3=$(JARS=("$LIB_DIR"/*.jar); IFS=:; echo "${JARS[*]}")

CFG=$MONITOR_BASE/config

$JAVA_HOME/bin/java -DINSTANCE=pro1 -cp "$CFG:$CP3" -Dlogback.configurationFile=/$CFG/logbak-pro1.xml com.founder.monitor.MainApp pro1
