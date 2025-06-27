import os
import logging

# 配置日志格式
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# 获取所有环境变量
env_vars = os.environ

logging.info('获取所有环境变量：')
for key, value in env_vars.items():
    # logging.info(f'{key}: {value}')
    pass

# # 设置环境变量
# os.environ['MY_VARIABLE'] = 'Hello, World!'
# logging.info('环境变量 MY_VARIABLE 已设置。')
#
# # 获取环境变量
# my_variable = os.environ.get('MY_VARIABLE')
# logging.info(f'获取到的环境变量 MY_VARIABLE: {my_variable}')

# 要添加到 PATH 的新路径
new_path = '/your/new/path'

# 获取当前 PATH
current_path = os.environ.get('PATH', '')

# 设置临时环境变量
os.environ['CUDA_VISIBLE_DEVICES'] = '0'

# 永久设置环境变量，通过命令行将环境变量写入配置
import os

path = r"E:\env"
command = r"setx WORK1 %s /m" % path
os.system(command)


# 添加PATH路径
def add_env_to_path(name: str, value: str, tmp: bool = False):
    pass


# 检查新路径是否已存在于 PATH 中
if new_path not in current_path:
    # 添加新路径到 PATH
    os.environ['PATH'] = current_path + os.pathsep + new_path
    logging.info(f'已将路径添加到 PATH: {new_path}')
else:
    logging.info(f'路径已存在于 PATH 中，忽略添加: {new_path}')

# 打印当前 PATH
logging.info(f'当前 PATH: {os.environ["PATH"]}')
