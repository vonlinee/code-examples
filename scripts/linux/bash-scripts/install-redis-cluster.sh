#!/bin/bash

# 默认安装目录为当前执行路径
INSTALL_DIR="$(pwd)/redis-cluster"

usage() {
    echo "Usage: $0 --source <redis_source> --nodes <number_of_nodes> [--install_dir <install_directory>]"
    exit 1
}

# 解析命令行参数
while [[ "$#" -gt 0 ]]; do
    case $1 in
        --source) REDIS_SOURCE="$2"; shift ;;
        --nodes) NODE_COUNT="$2"; shift ;;
        --install_dir) INSTALL_DIR=$(realpath "$2"); shift ;;
        *) usage ;;
    esac
    shift
done

# 参数检查
if [[ -z "$REDIS_SOURCE" || -z "$NODE_COUNT" ]]; then
    usage
fi

# 检查 Redis 源目录是否存在
if [[ ! -d "$REDIS_SOURCE" ]]; then
    echo "Error: Redis source directory '$REDIS_SOURCE' does not exist."
    exit 1
fi

# 创建 redis-cluster 目录
if [[ ! -d "$INSTALL_DIR" ]]; then
    mkdir -p "$INSTALL_DIR"
    echo "Created directory: $INSTALL_DIR"
else
    echo "Directory already exists: $INSTALL_DIR"
fi

# 复制 Redis 源目录并修改配置
for ((i=1; i<=NODE_COUNT; i++)); do
    NODE_DIR="$INSTALL_DIR/redis-node$i"

    # 复制源目录
    cp -r "$REDIS_SOURCE" "$NODE_DIR"
    echo "Copied Redis source to: $NODE_DIR"

    # 修改 redis.conf 文件
    CONF_FILE="$NODE_DIR/redis.conf"

    if [[ -f "$CONF_FILE" ]]; then
        PORT=$((6378 + i))  # 从 6379 开始递增
        DIR="$NODE_DIR"
        PIDFILE="/var/run/redis637${i}.pid"
        CLUSTER_CONFIG_FILE="nodes-$i.conf"

        # 更新配置文件
        sed -i -e "s/^port .*/port $PORT/" \
               -e "s|^dir .*|dir $DIR|" \
               -e "s|^cluster-config-file .*|cluster-config-file $DIR/$CLUSTER_CONFIG_FILE|" \
               -e "s|^pidfile .*|pidfile $PIDFILE|" "$CONF_FILE"

        echo "Updated configuration for node $i:"
        echo "  Port: $PORT"
        echo "  Dir: $DIR"
        echo "  Cluster Config File: $DIR/$CLUSTER_CONFIG_FILE"
        echo "  PID File: $PIDFILE"
    else
        echo "Error: Configuration file '$CONF_FILE' does not exist."
        exit 1
    fi
done

# 创建启动脚本
START_SCRIPT="$INSTALL_DIR/start-cluster.sh"
echo "#!/bin/bash" > "$START_SCRIPT"
echo "echo 'Starting Redis cluster nodes...'" >> "$START_SCRIPT"

for ((i=1; i<=NODE_COUNT; i++)); do
    echo "redis-server $INSTALL_DIR/redis-node$i/redis.conf &" >> "$START_SCRIPT"
done

chmod +x "$START_SCRIPT"
echo "Created start script: $START_SCRIPT"

# 创建清理脚本
CLEANUP_SCRIPT="$INSTALL_DIR/cleanup.sh"
echo "#!/bin/bash" > "$CLEANUP_SCRIPT"
echo "echo 'Cleaning up Redis cluster...'" >> "$CLEANUP_SCRIPT"

for ((i=1; i<=NODE_COUNT; i++)); do
    NODE_DIR="$INSTALL_DIR/redis-node$i"
    echo "rm -rf $NODE_DIR" >> "$CLEANUP_SCRIPT"
done

chmod +x "$CLEANUP_SCRIPT"
echo "Created cleanup script: $CLEANUP_SCRIPT"

echo "Redis cluster setup completed!"