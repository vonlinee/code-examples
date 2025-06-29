import logging
import os
import subprocess
import platform
import sys


def set_console_encoding_utf8_windows():
    if os.name == 'nt':  # Windows
        os.system('chcp 65001')  # 设置为 UTF-8
        sys.stdout.reconfigure(encoding='utf-8')


def set_console_encoding_utf8_unix():
    if sys.stdout.encoding != 'utf-8':
        sys.stdout.reconfigure(encoding='utf-8')
    print("Console encoding set to UTF-8.")


# 配置日志格式
def init_logging():
    logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')


# 设置日志配置
def init_file_logging():
    logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s',
                        filename='scripts.log')


# 获取所有环境变量
def get_all_env_dict():
    # 获取所有环境变量
    env_vars = os.environ
    envs = {}
    for key, value in env_vars.items():
        # logging.info(f'{key}: {value}')
        envs[key] = value
        pass
    return envs


def get_path_env_items() -> list[str]:
    path_items = os.environ['PATH']
    return path_items.split(";")


# 临时设置环境变量
def set_env_temp(name: str, value: str):
    os.environ[name] = value


# command = r"setx WORK1 %s /m" % path
# os.system(command)
def set_env_on_windows(vars_dict):
    for var_name, var_value in vars_dict.items():
        subprocess.run(['setx', var_name, var_value], check=True)
        print(f"永久设置环境变量: {var_name}={var_value}")
    print("变量在新打开的命令提示符窗口中生效")


def set_env_on_unix(vars_dict):
    # 获取用户的主目录
    home_dir = os.path.expanduser("~")
    bashrc_path = os.path.join(home_dir, '.bashrc')  # 或 '.bash_profile'

    # 检查文件是否存在并写入环境变量
    with open(bashrc_path, 'a') as file:
        for var_name, var_value in vars_dict.items():
            file.write(f'\nexport {var_name}="{var_value}"\n')
            print(f"永久设置环境变量: {var_name}={var_value}. 请重新启动终端以生效。")
    print("修改的变量需要重新启动终端或执行 source ~/.bashrc 使更改生效")


def set_env(vars_dict: dict[str, str]):
    os_type = platform.system()
    if os_type == "Windows":
        set_env_on_windows(vars_dict)
    elif os_type in ["Linux", "Darwin"]:  # Darwin 是 macOS 的标识
        set_env_on_unix(vars_dict)
    else:
        print("不支持的操作系统。")


def add_to_env_path_windows(new_path: str):
    current_path = os.environ.get("PATH", "")
    current_paths = os.environ.get("PATH", "").split(";")
    if new_path in current_paths:
        print(f"路径 '{new_path}' 已存在于 PATH 中。")
    else:
        subprocess.run(['setx', 'PATH', current_path + ';' + new_path], check=True)
        print(f"成功将路径 '{new_path}' 添加到 PATH 中。")


def add_to_env_path_unix(new_path: str):
    home_dir = os.path.expanduser("~")
    bashrc_path = os.path.join(home_dir, '.bashrc')  # 或 '.bash_profile'

    # 检查当前 PATH
    current_path = os.environ.get("PATH", "")
    if new_path in current_path:
        print(f"路径 '{new_path}' 已存在于 PATH 中。")
    else:
        with open(bashrc_path, 'a') as file:
            file.write(f'\nexport PATH="$PATH:{new_path}"\n')
        print(f"成功将路径 '{new_path}' 添加到 PATH 中。请重新启动终端以生效。")


def add_to_env_path(new_path: str):
    os_type = platform.system()
    if os_type == "Windows":
        add_to_env_path_windows(new_path)
    elif os_type in ["Linux", "Darwin"]:  # Darwin 是 macOS 的标识
        add_to_env_path_unix(new_path)
    else:
        print("不支持的操作系统。")


def remove_from_path_windows(paths_to_remove: list[str]):
    current_path = os.environ.get("PATH", "")
    new_path = current_path
    for path in paths_to_remove:
        if path in new_path:
            new_path = new_path.replace(path + ';', '').replace(';' + path, '').replace(path, '')
            logging.info(f"Removed '{path}' from PATH.")
        else:
            logging.warning(f"'{path}' not found in PATH.")
    subprocess.run(['setx', 'PATH', new_path], check=True)
    logging.info("Successfully updated PATH.")


def remove_from_path_unix(paths_to_remove: list[str]):
    home_dir = os.path.expanduser("~")
    bashrc_path = os.path.join(home_dir, '.bashrc')  # 或 '.bash_profile'

    with open(bashrc_path, 'r') as file:
        lines = file.readlines()

    current_path = os.environ.get("PATH", "")
    new_path = current_path

    for path in paths_to_remove:
        if path in new_path:
            new_path = new_path.replace(path + ':', '').replace(':' + path, '').replace(path, '')
            logging.info(f"Removed '{path}' from PATH.")
        else:
            logging.warning(f"'{path}' not found in PATH.")

    # 更新 .bashrc 文件
    with open(bashrc_path, 'w') as file:
        for line in lines:
            if 'export PATH=' not in line:
                file.write(line)
        file.write(f'\nexport PATH="{new_path}"\n')

    logging.info("Successfully updated PATH. Please restart the terminal for changes to take effect.")


def remove_from_path(paths_to_remove: list[str]):
    os_type = platform.system()
    if os_type == "Windows":
        remove_from_path_windows(paths_to_remove)
    elif os_type in ["Linux", "Darwin"]:  # Darwin 是 macOS 的标识
        remove_from_path_unix(paths_to_remove)
    else:
        logging.error("Unsupported operating system.")


if __name__ == "__main__":
    set_console_encoding_utf8_windows()

    add_to_env_path("D:\\Develop")
