import os
import shutil


# 复制源目录下的所有文件到目标目录
def copy_directory(source_dir, target_dir):
    # 检查源目录是否存在
    if not os.path.exists(source_dir):
        return
    # 创建目标目录（如果不存在）
    if not os.path.exists(target_dir):
        os.makedirs(target_dir)

    # 遍历源目录中的所有文件和子目录
    for item in os.listdir(source_dir):
        source_item = os.path.join(source_dir, item)
        target_item = os.path.join(target_dir, item)
        # 复制文件或目录
        if os.path.isfile(source_item):
            shutil.copy2(source_item, target_item)  # 复制文件及其元数据
            print(f"Copied file: {source_item} to {target_item}")
        elif os.path.isdir(source_item):
            shutil.copytree(source_item, target_item)  # 递归复制目录
            print(f"Copied directory: {source_item} to {target_item}")
    return target_dir


# 更新 Redis 配置文件中的指定配置项
def config_set(config_file, new_config):
    try:
        # 读取原始配置文件内容
        with open(config_file, 'r') as file:
            config_lines = file.readlines()
        # 更新配置项
        with open(config_file, 'w') as file:
            for line in config_lines:
                key_value = line.split(' ', 1)  # 分割为键和值
                if len(key_value) < 2:
                    file.write(line)  # 如果没有值，直接写入
                    continue
                key = key_value[0]
                if key in new_config:
                    file.write(f"{key} {new_config[key]}\n")
                    print(f"Updated: {key} to {new_config[key]}")
                else:
                    file.write(line)  # 保持原样
    except Exception as e:
        print(f"Error updating config file: {e}")


def install_cluster_nodes(source, n):
    return


if __name__ == "__main__":
    redis_install_dir = ""
    n = 6

    for i in range(n):
        location = copy_directory(redis_install_dir, "redis-cluster/redis-node" + str(i))

        config_set(location + "/", {
            "port": "6380",
            "dir": "/var/lib/redis",
            "maxmemory": "256mb",
        })
