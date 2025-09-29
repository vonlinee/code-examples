#!/usr/bin/env python3
"""
RocketMQ 多主多从集群部署脚本
支持 Linux 和 Windows 系统，无三方依赖，不使用类
"""

import argparse
import os
import sys
import shutil
import subprocess
import platform
import socket
import json
from pathlib import Path

# 全局配置
SYSTEM = platform.system().lower()
IS_WINDOWS = SYSTEM == "windows"
IS_LINUX = SYSTEM == "linux"
ROCKETMQ_VERSION = "4.9.7"
NAME_SERVER_PORT = 9876
BROKER_PORT_START = 10911
BROKER_HA_PORT_START = 10912


def check_java_installed():
    """检查 Java 是否已安装"""
    try:
        result = subprocess.run(
            ["java", "-version"],
            capture_output=True,
            text=True,
            check=False
        )
        if result.returncode == 0:
            print("✓ Java 已安装")
            return True
        else:
            print("✗ Java 未安装或配置不正确")
            return False
    except FileNotFoundError:
        print("✗ Java 未安装")
        return False


def check_port_available(port):
    """检查端口是否可用"""
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.settimeout(1)
            result = s.connect_ex(('127.0.0.1', port))
            return result != 0
    except socket.error:
        return False


def setup_directories(base_dir, rocketmq_dir, deploy_dir):
    """创建必要的目录结构"""
    directories = [base_dir, rocketmq_dir, deploy_dir]

    for directory in directories:
        try:
            directory.mkdir(parents=True, exist_ok=True)
            print(f"✓ 创建目录: {directory}")
        except OSError as e:
            print(f"✗ 创建目录失败 {directory}: {e}")
            sys.exit(1)


def download_file(url, target_path):
    """使用标准库下载文件"""
    try:
        import urllib.request
        import ssl

        # 创建 SSL 上下文
        ssl_context = ssl.create_default_context()
        ssl_context.check_hostname = False
        ssl_context.verify_mode = ssl.CERT_NONE

        print(f"下载: {url}")
        print(f"保存到: {target_path}")

        with urllib.request.urlopen(url, context=ssl_context) as response:
            total_size = int(response.headers.get('content-length', 0))
            downloaded = 0
            block_size = 8192

            with open(target_path, 'wb') as f:
                while True:
                    buffer = response.read(block_size)
                    if not buffer:
                        break
                    downloaded += len(buffer)
                    f.write(buffer)

                    # 显示下载进度
                    if total_size > 0:
                        percent = (downloaded / total_size) * 100
                        print(f"\r进度: {percent:.1f}% ({downloaded}/{total_size} bytes)", end='')

        print()  # 换行
        return True

    except Exception as e:
        print(f"\n✗ 下载失败: {e}")
        return False


def extract_zip(zip_path, extract_to):
    """使用标准库解压 ZIP 文件"""
    try:
        import zipfile

        print(f"解压: {zip_path}")
        with zipfile.ZipFile(zip_path, 'r') as zip_ref:
            zip_ref.extractall(extract_to)
        return True
    except Exception as e:
        print(f"✗ 解压失败: {e}")
        return False


def download_rocketmq(base_dir, rocketmq_dir, rocketmq_version, force=False):
    """下载 RocketMQ"""
    rocketmq_zip = base_dir / f"rocketmq-{rocketmq_version}.zip"
    extracted_dir_name = f"rocketmq-all-{rocketmq_version}-bin-release"
    extracted_dir = base_dir / extracted_dir_name

    # 检查是否已存在
    if not force and rocketmq_dir.exists() and any(rocketmq_dir.iterdir()):
        print("✓ RocketMQ 已存在，跳过下载")
        return True

    # 构建下载URL
    download_url = f"https://archive.apache.org/dist/rocketmq/{rocketmq_version}/rocketmq-all-{rocketmq_version}-bin-release.zip"

    # 下载文件
    if not download_file(download_url, rocketmq_zip):
        return False

    # 解压文件
    if not extract_zip(rocketmq_zip, base_dir):
        return False

    # 移动文件到目标目录
    try:
        if extracted_dir.exists():
            # 清空目标目录
            if rocketmq_dir.exists():
                shutil.rmtree(rocketmq_dir)
            shutil.move(str(extracted_dir), str(rocketmq_dir))

        # 清理压缩文件
        if rocketmq_zip.exists():
            rocketmq_zip.unlink()

        print("✓ RocketMQ 下载和解压完成")
        return True

    except Exception as e:
        print(f"✗ 文件移动失败: {e}")
        return False


def make_executable(path):
    """在 Linux 上使文件可执行"""
    if IS_LINUX:
        try:
            st = os.stat(path)
            os.chmod(path, st.st_mode | 0o111)  # 添加执行权限
        except OSError as e:
            print(f"✗ 设置执行权限失败 {path}: {e}")


def generate_name_server_config(deploy_dir, rocketmq_dir, instance_id, port):
    """生成 NameServer 配置"""
    config_dir = deploy_dir / f"namesrv{instance_id}"

    try:
        config_dir.mkdir(exist_ok=True)

        # 创建日志目录
        logs_dir = config_dir / "logs"
        logs_dir.mkdir(exist_ok=True)

        # 创建启动脚本
        if IS_WINDOWS:
            script_path = config_dir / "start.bat"
            script_content = f"""@echo off
cd "{rocketmq_dir / 'bin'}"
set NAMESRV_ADDR=localhost:{port}
start "RocketMQ NameServer {instance_id}" cmd /c mqnamesrv.cmd -n localhost:{port}
echo NameServer {instance_id} 已启动，端口: {port}
"""
        else:
            script_path = config_dir / "start.sh"
            script_content = f"""#!/bin/bash
cd "{rocketmq_dir / 'bin'}"
export NAMESRV_ADDR=localhost:{port}
nohup ./mqnamesrv -n localhost:{port} > "{logs_dir / 'namesrv.log'}" 2>&1 &
echo $! > "{config_dir / 'pid'}"
echo "NameServer {instance_id} 已启动，端口: {port}, PID: $!"
"""

        with open(script_path, 'w', encoding='utf-8') as f:
            f.write(script_content)

        if not IS_WINDOWS:
            make_executable(script_path)

        print(f"✓ NameServer {instance_id} 配置生成完成，端口: {port}")
        return config_dir, port

    except Exception as e:
        print(f"✗ NameServer {instance_id} 配置生成失败: {e}")
        return None, None


def generate_broker_config(deploy_dir, rocketmq_dir, master_id, name_server_port, slave_id=None):
    """生成 Broker 配置"""
    is_slave = slave_id is not None
    role = "SLAVE" if is_slave else "MASTER"
    broker_name = f"broker-{master_id}" if not is_slave else f"broker-{master_id}-slave-{slave_id}"
    instance_name = f"broker{master_id}{f'_slave{slave_id}' if is_slave else ''}"

    config_dir = deploy_dir / instance_name

    try:
        config_dir.mkdir(exist_ok=True)

        # 创建存储目录
        store_dir = config_dir / "store"
        commitlog_dir = store_dir / "commitlog"
        consumequeue_dir = store_dir / "consumequeue"
        index_dir = store_dir / "index"

        for d in [store_dir, commitlog_dir, consumequeue_dir, index_dir]:
            d.mkdir(parents=True, exist_ok=True)

        # 创建日志目录
        logs_dir = config_dir / "logs"
        logs_dir.mkdir(exist_ok=True)

        # 分配端口
        port_offset = master_id * 10 + (slave_id if slave_id is not None else 0)
        broker_port = BROKER_PORT_START + port_offset
        broker_ha_port = BROKER_HA_PORT_START + port_offset

        # 检查端口是否可用
        if not check_port_available(broker_port):
            print(f"⚠ 端口 {broker_port} 已被占用，尝试自动调整")
            broker_port += 100

        # 生成配置文件
        config_content = f"""brokerClusterName = DefaultCluster
brokerName = broker-{master_id}
brokerId = {slave_id if is_slave else 0}
deleteWhen = 04
fileReservedTime = 48
brokerRole = {role}
flushDiskType = ASYNC_FLUSH
brokerIP1 = 127.0.0.1
listenPort = {broker_port}
haListenPort = {broker_ha_port}
namesrvAddr=localhost:{name_server_port}
storePathRootDir={store_dir}
storePathCommitLog={commitlog_dir}
storePathConsumeQueue={consumequeue_dir}
storePathIndex={index_dir}
"""

        config_file = config_dir / "broker.conf"
        with open(config_file, 'w', encoding='utf-8') as f:
            f.write(config_content)

        # 创建启动脚本
        if IS_WINDOWS:
            script_path = config_dir / "start.bat"
            script_content = f"""@echo off
cd "{rocketmq_dir / 'bin'}"
set NAMESRV_ADDR=localhost:{name_server_port}
start "RocketMQ Broker {instance_name}" cmd /c mqbroker.cmd -c "{config_file}"
echo Broker {instance_name} 已启动，端口: {broker_port}
"""
        else:
            script_path = config_dir / "start.sh"
            script_content = f"""#!/bin/bash
cd "{rocketmq_dir / 'bin'}"
export NAMESRV_ADDR=localhost:{name_server_port}
nohup ./mqbroker -c "{config_file}" > "{logs_dir / 'broker.log'}" 2>&1 &
echo $! > "{config_dir / 'pid'}"
echo "Broker {instance_name} 已启动，端口: {broker_port}, PID: $!"
"""

        with open(script_path, 'w', encoding='utf-8') as f:
            f.write(script_content)

        if not IS_WINDOWS:
            make_executable(script_path)

        print(f"✓ Broker {instance_name} 配置生成完成，端口: {broker_port}")
        return config_dir, broker_name, broker_port

    except Exception as e:
        print(f"✗ Broker {instance_name} 配置生成失败: {e}")
        return None, None, None


def setup_environment_variables(base_dir, rocketmq_dir, name_server_port):
    """设置环境变量"""
    env_vars = {
        "ROCKETMQ_HOME": str(rocketmq_dir),
        "NAMESRV_ADDR": f"localhost:{name_server_port}",
    }

    # 生成环境变量设置脚本
    if IS_WINDOWS:
        env_script = base_dir / "set_env.bat"
        content = "@echo off\n"
        for key, value in env_vars.items():
            content += f'set {key}={value}\n'
            content += f'echo 设置环境变量: {key}={value}\n'
        content += "echo 环境变量设置完成\n"
        content += "echo 请手动运行: set PATH=%ROCKETMQ_HOME%\\bin;%PATH%\n"
    else:
        env_script = base_dir / "set_env.sh"
        content = "#!/bin/bash\n"
        for key, value in env_vars.items():
            content += f'export {key}="{value}"\n'
            content += f'echo "设置环境变量: {key}={value}"\n'
        content += 'export PATH="$ROCKETMQ_HOME/bin:$PATH"\n'
        content += 'echo "环境变量设置完成"\n'

    try:
        with open(env_script, 'w', encoding='utf-8') as f:
            f.write(content)

        if not IS_WINDOWS:
            make_executable(env_script)

        print(f"✓ 环境变量设置脚本已生成: {env_script}")
        return True
    except Exception as e:
        print(f"✗ 环境变量设置脚本生成失败: {e}")
        return False


def generate_cluster_management_scripts(base_dir, rocketmq_dir, name_servers, brokers):
    """生成集群管理脚本"""
    try:
        # 生成启动所有节点的脚本
        if IS_WINDOWS:
            start_all_script = base_dir / "start_all.bat"
            content = "@echo off\n"
            content += "echo 启动 RocketMQ 集群...\n"
            for ns_dir, port in name_servers:
                content += f'echo 启动 NameServer (端口: {port})...\n'
                content += f'start "NameServer" cmd /c "{ns_dir / "start.bat"}"\n'
            content += "echo 等待 NameServer 启动...\n"
            content += "timeout /t 5 /nobreak\n"
            for broker_dir, name, port in brokers:
                content += f'echo 启动 Broker {name} (端口: {port})...\n'
                content += f'start "Broker" cmd /c "{broker_dir / "start.bat"}"\n'
            content += "echo 所有节点启动命令已发送\n"
            content += "echo 请检查各节点的日志文件确认启动状态\n"
        else:
            start_all_script = base_dir / "start_all.sh"
            content = "#!/bin/bash\n"
            content += "echo \"启动 RocketMQ 集群...\"\n"
            for ns_dir, port in name_servers:
                content += f'echo "启动 NameServer (端口: {port})..."\n'
                content += f'"{ns_dir / "start.sh"}" &\n'
            content += "echo \"等待 NameServer 启动...\"\n"
            content += "sleep 5\n"
            for broker_dir, name, port in brokers:
                content += f'echo "启动 Broker {name} (端口: {port})..."\n'
                content += f'"{broker_dir / "start.sh"}" &\n'
            content += "echo \"所有节点启动命令已发送\"\n"
            content += "echo \"请检查各节点的日志文件确认启动状态\"\n"

        with open(start_all_script, 'w', encoding='utf-8') as f:
            f.write(content)

        if not IS_WINDOWS:
            make_executable(start_all_script)

        # 生成停止所有节点的脚本
        if IS_WINDOWS:
            stop_all_script = base_dir / "stop_all.bat"
            content = "@echo off\n"
            content += "echo 停止 RocketMQ 集群...\n"
            content += f'cd "{rocketmq_dir / "bin"}"\n'
            content += "mqshutdown.cmd broker\n"
            content += "mqshutdown.cmd namesrv\n"
            content += "echo 集群已停止\n"
        else:
            stop_all_script = base_dir / "stop_all.sh"
            content = "#!/bin/bash\n"
            content += "echo \"停止 RocketMQ 集群...\"\n"
            content += f'cd "{rocketmq_dir / "bin"}"\n'
            content += "./mqshutdown broker\n"
            content += "./mqshutdown namesrv\n"
            # 清理 pid 文件
            for ns_dir, _ in name_servers:
                content += f'if [ -f "{ns_dir / "pid"}" ]; then\n'
                content += f'  rm -f "{ns_dir / "pid"}"\n'
                content += 'fi\n'
            for broker_dir, _, _ in brokers:
                content += f'if [ -f "{broker_dir / "pid"}" ]; then\n'
                content += f'  rm -f "{broker_dir / "pid"}"\n'
                content += 'fi\n'
            content += "echo \"集群已停止\"\n"

        with open(stop_all_script, 'w', encoding='utf-8') as f:
            f.write(content)

        if not IS_WINDOWS:
            make_executable(stop_all_script)

        print(f"✓ 集群管理脚本已生成:")
        print(f"  - 启动所有节点: {start_all_script}")
        print(f"  - 停止所有节点: {stop_all_script}")
        return True

    except Exception as e:
        print(f"✗ 集群管理脚本生成失败: {e}")
        return False


def generate_cluster_info(base_dir, rocketmq_dir, name_servers, brokers, rocketmq_version):
    """生成集群信息文件"""
    try:
        cluster_info = {
            "rocketmq_version": rocketmq_version,
            "name_servers": [{"id": i, "port": port, "config_dir": str(dir)} for i, (dir, port) in
                             enumerate(name_servers)],
            "brokers": [{"name": name, "port": port, "config_dir": str(dir)} for dir, name, port in brokers],
            "environment": {
                "ROCKETMQ_HOME": str(rocketmq_dir),
                "NAMESRV_ADDR": f"localhost:{name_servers[0][1] if name_servers else NAME_SERVER_PORT}"
            }
        }

        info_file = base_dir / "cluster_info.json"
        with open(info_file, 'w', encoding='utf-8') as f:
            json.dump(cluster_info, f, indent=2, ensure_ascii=False)

        print(f"✓ 集群信息已保存到: {info_file}")
        return True
    except Exception as e:
        print(f"✗ 集群信息生成失败: {e}")
        return False


def parse_arguments():
    """解析命令行参数"""
    parser = argparse.ArgumentParser(description="部署 RocketMQ 多主多从集群")
    parser.add_argument("--masters", type=int, required=True, help="主节点数量")
    parser.add_argument("--slaves", type=int, required=True, help="每个主节点的从节点数量")
    parser.add_argument("--version", type=str, default=ROCKETMQ_VERSION,
                        help=f"RocketMQ 版本 (默认: {ROCKETMQ_VERSION})")
    parser.add_argument("--base-dir", type=str, default="./rocketmq_cluster",
                        help="部署基础目录 (默认: ./rocketmq_cluster)")
    parser.add_argument("--force", action="store_true",
                        help="强制重新下载和部署")
    parser.add_argument("--name-server-port", type=int, default=NAME_SERVER_PORT,
                        help=f"NameServer 起始端口 (默认: {NAME_SERVER_PORT})")

    return parser.parse_args()


def main():
    """主函数"""
    try:
        # 解析参数
        args = parse_arguments()
        # 验证参数
        if args.masters <= 0:
            print("错误: 主节点数量必须大于 0")
            sys.exit(1)
        if args.slaves < 0:
            print("错误: 从节点数量不能为负数")
            sys.exit(1)

        # 检查 Java
        if not check_java_installed():
            print("请先安装 Java 8 或更高版本")
            sys.exit(1)

        # 设置目录路径
        base_dir = Path(args.base_dir).resolve()
        rocketmq_dir = base_dir / "rocketmq"
        deploy_dir = base_dir / "deploy"

        # 创建目录结构
        setup_directories(base_dir, rocketmq_dir, deploy_dir)

        # 部署 NameServer（通常只需要一个）
        name_servers = []
        ns_config = generate_name_server_config(deploy_dir, rocketmq_dir, 0, args.name_server_port)
        if ns_config[0]:
            name_servers.append(ns_config)

        # 部署 Broker
        brokers = []
        for master_id in range(args.masters):
            # 主节点
            broker_config = generate_broker_config(deploy_dir, rocketmq_dir, master_id, args.name_server_port)
            if broker_config[0]:
                brokers.append(broker_config)

            # 从节点
            for slave_id in range(args.slaves):
                broker_config = generate_broker_config(deploy_dir, rocketmq_dir, master_id, args.name_server_port,
                                                       slave_id)
                if broker_config[0]:
                    brokers.append(broker_config)

        # 设置环境变量
        setup_environment_variables(base_dir, rocketmq_dir, args.name_server_port)
        # 生成管理脚本
        generate_cluster_management_scripts(base_dir, rocketmq_dir, name_servers, brokers)
        # 生成集群信息
        generate_cluster_info(base_dir, rocketmq_dir, name_servers, brokers, args.version)
        print("=" * 60)
        print("✓ RocketMQ 集群部署完成！")
        print("=" * 60)
        print(f"部署目录: {base_dir}")
        print(f"NameServer 地址: localhost:{args.name_server_port}")
        print(f"主节点数量: {args.masters}")
        print(f"每个主节点的从节点数量: {args.slaves}")
        print(f"总 Broker 节点数量: {len(brokers)}")
        print("\n下一步操作:")
        print("1. 运行 set_env 脚本设置环境变量")
        print("2. 运行 start_all 脚本启动所有节点")
        print("3. 运行 stop_all 脚本停止所有节点")
        print("4. 查看 cluster_info.json 获取详细集群信息")
    except KeyboardInterrupt:
        print("\n用户中断操作")
        sys.exit(1)
    except Exception as e:
        print(f"部署过程中发生错误: {e}")
        import traceback
        traceback.print_exc()
        sys.exit(1)


if __name__ == "__main__":
    main()
