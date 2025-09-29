from pathlib import Path

# 主节点数量
MASTER_COUNT = 1
# 从节点数量
SLAVE_COUNT = 2
# RocketMQ二进制包路径
ROCKETMQ_PATH = "下载的RocketMQ二进制包路径"
# 集群根目录
CLUSTER_HOME = "D:/tmp/rocketmqCluster"


def write_broker_config(cluster_name: str,
                        broker_id: int,
                        is_slave: bool,
                        location: str,
                        name_srv_addr: str):
    role = "SLAVE" if is_slave else "ASYNC_MASTER"
    broker_name = f"${"slave-" if is_slave else "master"}broker${broker_id}"
    store_path_root_dir = f"{CLUSTER_HOME}/{broker_name}/store"
    store_path_commit_log = f"{CLUSTER_HOME}/{broker_name}/store/commitlog"
    with open(location, "w", encoding='utf-8') as f:
        f.write(f"""
# 此Broker所在的集群名称
brokerClusterName = ${cluster_name}
# Broker 节点名称
brokerName = ${broker_name}
# Broker的ID
brokerId = ${id}
# Broker的角色
brokerRole = ${role}
deleteWhen = 04
fileReservedTime = 48
# 异步刷盘
flushDiskType = ASYNC_FLUSH
# 消息持久化存储根路径
storePathRootDir=${store_path_root_dir}
# 消息持久化存储路径
storePathCommitLog=${store_path_commit_log}
# 连接NameServer的地址
namesrvAddr=${name_srv_addr}
brokerIP1=127.0.0.1
autoCreateTopicEnable=true
        """)


def write_nameserver_config(location: str):
    pass


def main():
    write_nameserver_config()
