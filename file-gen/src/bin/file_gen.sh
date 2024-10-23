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
DATA_PATH="$PWD/data"
# 检查路径是否存在
if [ ! -d "$DATA_PATH" ]; then
    # 路径不存在，则创建它
    mkdir -p "$DATA_PATH"

    # 检查mkdir命令是否成功
    if [ $? -eq 0 ]; then
        echo "路径 $DATA_PATH 已成功创建。"
    else
        echo "创建路径 $DATA_PATH 失败。" >&2
        exit 1
    fi
else
    # 路径已存在，不处理
    echo "路径 $DATA_PATH 已存在，不处理。"
fi
PARALLELISM=$1
TOTAL_COUNT=$2
ROWS_PER_FILE=$3
nohup java -Dparallelism=$PARALLELISM -DtotalCount=$TOTAL_COUNT -DrowsPerFile=$ROWS_PER_FILE -DfileOutputDir=$DATA_PATH -jar "$PWD/jar/file-gen.jar" >> "$LOG_PATH/file-gen.log" 2>&1 &