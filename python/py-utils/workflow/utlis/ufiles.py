import mimetypes
import os  # 系统
import shutil


class FilePath(object):

    @staticmethod
    def exists(filepath: str) -> bool:
        return os.path.exists(filepath)

    @staticmethod
    def isdir(filepath) -> bool:
        return os.path.isdir(filepath)

    @staticmethod
    def isfile(filepath) -> bool:
        return os.path.isfile(filepath)

    @staticmethod
    def join() -> str:
        return os.path.join("")

    @staticmethod
    def can_read():
        pass


class Files(object):

    @staticmethod
    def read_to_string(filepath: str) -> str:
        pass

    @staticmethod
    def list_files(parent_dir: str) -> None:
        pass

    @staticmethod
    def delete_file(filepath: str) -> None:
        if os.path.isdir(filepath):
            os.rmdir(filepath)
        if os.path.isfile(filepath):
            os.remove(filepath)


# 定义函数
def list_all_files(rootdir):
    import os
    _files = []
    # 列出文件夹下所有的目录与文件
    file_list = os.listdir(rootdir)
    for i in range(0, len(file_list)):
        # 构造路径
        path = os.path.join(rootdir, file_list[i])
        # 判断路径是否为文件目录或者文件
        # 如果是目录则继续递归
        if os.path.isdir(path):
            _files.append(path)
            _files.extend(list_all_files(path))
        if os.path.isfile(path):
            _files.append(path)
    return _files


def delete_java_files(root_directory):
    files = list_all_files(root_directory)
    for i in range(0, len(files)):
        abs_path = files[i]
        if os.path.isdir(abs_path):
            if abs_path.endswith(".settings") or abs_path.endswith(".idea") or abs_path.endswith("bin"):
                try:
                    shutil.rmtree(abs_path)  # 递归删除文件夹
                    print("删除目录成功： " + abs_path)
                except IOError as error:
                    print("删除目录失败：" + abs_path + " " + error.__str__())
        if os.path.isfile(abs_path):
            if abs_path.endswith(".classpath") or abs_path.endswith(".project"):
                try:
                    os.remove(abs_path)
                    print("删除文件成功： " + abs_path)
                except IOError as error:
                    print("删除文件失败：" + abs_path + " " + error.__str__())


delete_java_files(r"D:\Develop\Projects\Github\code-example")
