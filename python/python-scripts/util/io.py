import requests
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

