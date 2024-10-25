#!/bin/bash
PWD=$(pwd)
source $PWD/conf/doris-cluster.conf
LOG_PATH="$PWD/log"
DATA_DIR="$PWD/data"
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
PARALLELISM=$1
nohup java -Dparallelism=$PARALLELISM -DdataDir=$DATA_DIR -DbeHost=$BE_HOST -DbeHttpPort=$BE_HTTP_PORT -Dusername=$USERNAME -Dpassword=$PASSWORD -Ddb=$DB -Dtable=$TABLE -jar "$PWD/jar/be-stream-load.jar" >> "$LOG_PATH/be-stream-load.log" 2>&1 &
