# 本脚本用于安装这些脚本可能会用到的一些三方库

import sys
import subprocess


def install_package(package):
    subprocess.check_call([sys.executable, '-m', 'pip', 'install', package])


def print_versions():
    # 输出 Python 版本
    print(f"Python version: {sys.version}")

    # 输出 pip 版本
    try:
        pip_version = subprocess.check_output([sys.executable, '-m', 'pip', '--version']).decode().strip()
        print(f"pip version: {pip_version}")
    except subprocess.CalledProcessError:
        print("pip is not installed.")

    # 输出已安装库的位置
    try:
        installed_packages = subprocess.check_output(
            [sys.executable, '-m', 'pip', 'list', '--format=freeze']).decode().strip().split('\n')
        for package in installed_packages:
            name, version = package.split('==')
            package_info = subprocess.check_output([sys.executable, '-m', 'pip', 'show', name]).decode().strip().split(
                '\n')
            for line in package_info:
                if line.startswith("Location:"):
                    print(f"{name}=={version} is installed at: {line.split(': ')[1]}")
                    break
    except subprocess.CalledProcessError:
        print("Failed to retrieve installed packages.")


if __name__ == "__main__":
    # 要安装的库
    packages_to_install = ["requests", "numpy"]  # 替换为你想要安装的库

    # 安装库
    for package in packages_to_install:
        install_package(package)

    # 输出版本信息
    print_versions()
