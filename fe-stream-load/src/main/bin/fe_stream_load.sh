#!/bin/bash
PWD=$(pwd)
LOG_PATH="$PWD/log"
# 检查路径是否存在
if [ ! -d "$LOG_PATH" ]; then
    # 路径不存在，则创建它
    mkdir -p "$LOG_PATH"

    # 检查mkdir命令是否成功
    if [ $? -eq 0 ]; then
        echo "路径 $LOG_PATH 已成功创建。"
    else
        echo "创建路径 $LOG_PATH 失败。" >&2
        exit 1
    fi
else
    # 路径已存在，不处理
    echo "路径 $LOG_PATH 已存在，不处理。"
fi
FENODES=$1
USER=$2
PASSWORD=$3
DATA_DIR=$4
PARALLELISM=$5
# 检查路径是否存在
#if [ ! -d "$DATA_DIR" ]; then
#    echo "$DATA_DIR 路径不存在。" >&2
#    exit 1
#else
#fi
nohup java -Dparallelism=$PARALLELISM -Dfenodes=$FENODES -Duser=$USER -Dpassword=$PASSWORD -DdataDir=$DATA_DIR -jar "$PWD/jar/fe-stream-load.jar" >> "$LOG_PATH/fe-stream-load.log" 2>&1 &
