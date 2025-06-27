def config_set(config_file, new_config):
    try:
        # 读取原始配置文件内容
        with open(config_file, 'r') as file:
            config_lines = file.readlines()
        # 更新配置项
        with open(new_config, 'w') as file:
            for line in config_lines:
                if line.startswith("#") or len(line) == 0:
                    continue
                file.write(line)

    except Exception as e:
        print(f"Error updating config file: {e}")


config_set("C:/Users/lenovo/Desktop/redis.conf", "C:/Users/lenovo/Desktop/redis1.conf")
