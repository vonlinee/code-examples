import requests
import os
from tqdm import tqdm


def download_file(url, destination):
    # 发送请求
    response = requests.get(url, stream=True)
    total_size = int(response.headers.get('content-length', 0))

    # 使用 tqdm 显示进度条
    with open(destination, 'wb') as file, tqdm(
            desc=destination,
            total=total_size,
            unit='B',
            unit_scale=True,
            unit_divisor=1024,
    ) as bar:
        for data in response.iter_content(chunk_size=1024):
            file.write(data)
            bar.update(len(data))


def create_path_if_not_exists(path, mode=0o755):
    """
    创建路径，如果任意子路径不存在则进行创建
    
    Args:
        path (str): 要创建的路径
        mode (int): 目录权限模式，默认为0o755
    
    Returns:
        bool: 创建成功返回True，否则返回False
    """
    try:
        # 如果路径不存在，则创建
        if not os.path.exists(path):
            os.makedirs(path, mode=mode, exist_ok=True)
            print(f"已创建路径: {path}")
        else:
            # 检查路径是否为目录
            if not os.path.isdir(path):
                raise NotADirectoryError(f"路径 {path} 已存在但不是目录")
            print(f"路径已存在: {path}")
        return True
    except Exception as e:
        print(f"创建路径失败: {path}，错误: {str(e)}")
        return False


def create_hive_directories(hive_home):
    """
    创建Hive所需的所有标准目录
    
    Args:
        hive_home (str): Hive安装主目录
    
    Returns:
        bool: 所有目录创建成功返回True，否则返回False
    """
    directories = [
        os.path.join(hive_home, "conf"),
        os.path.join(hive_home, "lib"),
        os.path.join(hive_home, "bin"),
        os.path.join(hive_home, "scripts"),
        os.path.join(hive_home, "metastore_db"),
        os.path.join(hive_home, "logs"),
        os.path.join(hive_home, "hcatalog"),
        os.path.join(hive_home, "warehouse")
    ]
    
    success = True
    for directory in directories:
        if not create_path_if_not_exists(directory):
            success = False
    
    return success


def create_config_directories(config_file_path):
    """
    根据配置文件路径自动创建其父目录
    
    Args:
        config_file_path (str): 配置文件的完整路径
    
    Returns:
        bool: 目录创建成功返回True，否则返回False
    """
    # 获取配置文件所在的目录
    config_dir = os.path.dirname(config_file_path)
    if config_dir:
        return create_path_if_not_exists(config_dir)
    return True