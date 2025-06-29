import os
import zipfile
import requests


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


def unzip_file(zip_file_path, extract_to=None):
    # 如果没有指定解压路径，则使用当前目录
    if extract_to is None:
        extract_to = os.getcwd()

    # 创建解压目标文件夹（如果不存在）
    os.makedirs(extract_to, exist_ok=True)

    # 解压缩文件
    with zipfile.ZipFile(zip_file_path, 'r') as zip_ref:
        zip_ref.extractall(extract_to)

    print(f"文件 '{zip_file_path}' 已解压到 '{extract_to}'")


if __name__ == '__main__':
    download_file("", "")
