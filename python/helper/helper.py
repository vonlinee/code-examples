# -*- coding: utf-8 -*-
import sublime
import sublime_plugin

# 插件文件里只能导入Python内置模块，其他第三方模块需要以依赖的形式安装在本地
# import chardet    # 编码检测
# import xlrd       # 读Excel
# import xlwt       # 写Excel
import random       # 随机数
import json

def println(obj) -> None:
    print(obj)

def print_type(obj) -> None:
    print(type(obj))

# https://oktools.net/json
# view.run_command('example')

def get_selcted(view):
    sel = view.sel()
    reg = sel[0]  # print(type(reg))  <class 'sublime.Region'>
    sels = view.substr(reg)
    if sels == "":  # 未选中任何字符
        # reg = sublime.Region(0, view.size())
        reg = sublime.Region(0, 0) # 什么都不选
        sels = view.substr(reg) # 获取全部内容
    return reg, sels


# 分发处理逻辑
def dispatch_handler(handler_name: str, text: str) -> str:
    if handler_name == 'remove_blank':
        return remove_blank(text)
    return text


# 按换行符分割成多行文本列表
def str_to_lines(text) -> None:
    lines = []
    if text is not None:
        lines = text.split("\n")
    return lines


# https://blog.csdn.net/lamp_yang_3s533/article/details/96765402
def change_line_end_flag() -> None:
    return

# 将每行的字符串列表（不包含换行符）加上换行符
def lines_to_string(lines: list) -> str:
    return "\n".join(lines)


####################################################################### 
# 自定义工具函数：实现具体的功能
#######################################################################


# USER_NAME -> userName
def snake_to_camel_style(snake_str: str, upper_first=False) -> str:
    splits = snake_str.split("_")
    l = []
    for i in splits:
        l.append(i.lower().capitalize())
    s = "".join(l)
    if upper_first:
        return s[0].upper() + s[1:]
    else:
        return s[0].lower() + s[1:]


def camel_to_snake_style(camel_str: str, lower_all=False) -> str:
    import re
    snake_case = re.sub(r"(?P<key>[A-Z])", r"_\g<key>", camel_str)
    result = snake_case.strip('_')
    print("".join(result))
    if lower_all:
        return result.lower()
    return result.upper()

# 蛇形转大驼峰（帕斯卡）
def pascal_case_to_snake_case(camel_case: str):
    import re
    snake_case = re.sub(r"(?P<key>[A-Z])", r"_\g<key>", camel_case)
    return snake_case.lower().strip('_')

# 蛇形转大驼峰（帕斯卡）
def snake_to_pascal_case(snake_case: str, upper_first=False):
    words = snake_case.split('_')
    result = ''.join(word.title() for word in words)
    if upper_first:
        return result[0].upper() + result[1:]
    return result[0].lower() + result[1:]


def format_date_column(column_name: str) -> str:
    l = len(column_name)
    if column_name.endswith(','):
        column_name = column_name[0: l-1]
    return "DATE_FORMAT(" + column_name + ", '%Y-%m-%d') AS " + column_name + ","


def batch_format_date_column() -> None:
    lines = None
    with open('input.txt', 'r') as f:
        lines = f.readlines()
    new_lines = []
    for line in lines:
        new_line = format_date_column(line.strip())
        new_lines.append(new_line)
    with open('output.txt', 'w') as output:
        output.writelines([line + '\n' for line in new_lines])


def split_line_to_nline() -> None:
    """ 
    Args:
        input_file (str, optional): [input file contains one line ]. Defaults to './input.txt'.
    """
    line = None
    with open(input_file, 'r') as f:
        line = f.readline()
    strings = line.strip().split(delimeter)
    new_lines = []
    for s in strings:
        new_lines.append(s)
    with open('output.txt', 'w') as output:
        output.writelines([line.strip() + '\n' for line in new_lines])


def merge_nline_to_line() -> None:
    """[merge_nline_to_line]

    Args:
        input_file (str, optional): [description]. Defaults to './input.txt'.
        delimeter (str, optional): [description]. Defaults to ','.
    """
    lines = None
    with open(input_file, 'r') as f:
        lines = f.readlines()
    new_line = []
    for line in lines:
        line = line.replace('\n', '').strip()
        new_line.append(line)
    print(delimeter.join(new_line))

def generate_sql(template_name='Template1.xlsx') -> None:
    """[summary]
    Args:
        template_name ([type]): [description]
    Returns:
        [type]: [description]
    """
    excel_data = xlrd.open_workbook(EXCEL_TEMPLATE_DIR + template_name)
    table = excel_data.sheet_by_index(0)
    column_count = table.ncols
    row_count = table.nrows  # 表的行数
    l = []
    for i in range(1, row_count):
        row = table.row_values(i, start_colx=0, end_colx=None)
        l.append(row[1] + "," + " /*" + row[0] + "*/ \n")
    return "".join(l)


# def lines_to_empty_json(input_file: str = INPUT_TXT_FILE_PATH):
#     lines = None
#     with open(input_file, 'r') as f:
#         lines = f.readlines()
#     empty_key_json = {}
#     for line in lines:
#         empty_key_json[line.strip()] = ""
#     json_str = json.dumps(empty_key_json, sort_keys=True, indent=4, separators=(',', ': '))
#     print(json_str)



# Obj='C:\Users\Administrator\Desktop'   or 'C:\Users\Administrator\Desktop\chrome.exe'

def open_in_file_explorer(file_name: str) -> None:
    if file_name and os.path.exists(file_name):  #文件or 目录存在
        if os.path.isfile(file_name):
            import win32process
            try:   # 打开外部可执行程序
                win32process.CreateProcess(file_name, '',None , None , 0 ,win32process. CREATE_NO_WINDOW , None , None ,win32process.STARTUPINFO())
            except Exception, e:
                print(e)
        else:
            os.startfile(str(file_name))  # 打开目录
    else:  # 不存在的目录
        print('不存在的目录')

def print_random_phone_number(count: int = 1):
    # 产生随机手机号码
    # list1 = ['(移动)134', '(移动)135', '(移动)136', '(移动)137', '(移动)138', '(移动)139', '(移动)150',
    #         '(移动)151', '(移动)152', '(移动)157', '(移动)158', '(移动)159', '(移动)187', '(TD专用)188',
    #         '(联通)130', '(联通)131', '(联通)132', '(联通)155', '(联通)156', '(联通)185', '(联通)186',
    #         '(电信)133', '(电信)153', '(电信)180', '(电信)189', ]
    l = ['134', '135', '136', '137', '138', '139', '150',
        '151', '152', '157', '158', '159', '187', '188',
        '130', '131', '132', '155', '156', '185', '186',
        '133', '153', '180', '189',]
    haom = '' # 空字符串
    haoma1 = []    # 空列表
    for i in range(0, count):
        a = random.choice(l)   # 随机选择一个三位数作为手机号前三位
        b = '0123456789'   # 定义后面每位数字为0-9的数字
        for i in range(8):
            haoma1.append(random.choice(b))
            haom = ''.join(haoma1)
        print(a + haom)
        haoma1.clear()  # 清空上一次的输出


# 按指定步长递增数字
def increasing_num_by_step(start: int=0, end: int=20, step: int = 1) -> list:
    nums = []
    for x in range(start, end + 1, step):
        nums.append(x)
    return nums


# 按步长打印数字
def print_increasing_num_by_step(
    start: int=0, end: int=20, step: int = 1) -> list:
    nums = []
    for x in range(start, end + 1, step):
        print(x)


# 文本对齐，默认左对齐
def batch_aligen(aligenment: str= 'L')-> None:
    lines = None
    with open('./input.txt', 'r') as f:
        lines = f.readlines()
        max_len_str = max(lines, key=len)
        max_len = len(max_len_str)
        if aligenment == 'L':
            for line in lines:
                print(line.ljust(max_len))
        if aligenment == 'R':
            for line in lines:
                print(line.rjust(max_len))
        if aligenment == 'C':
            for line in lines:
                print(line.center(max_len))


####################################################################### 
# 自定义函数  END
#######################################################################

####################################################################### 
# 插件相关的自定义函数  START
#######################################################################

# 判断是行选还是列选
def decide_selection_model() -> int:
    return 1

####################################################################### 
# 插件相关的自定义函数  END
#######################################################################

####################################################################### 
# 插件命令 START
#######################################################################


# 将选中的所有字符转换为大写
class UpperAllCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        # print(type(txt)) <class 'str'>
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            new_lines.append(lines[i].upper())  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


# 去除所有除英文字母外的特殊字符
class RemoveAllSpecialCharacterCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        # print(type(txt)) <class 'str'>
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            new_line = re.sub('[\\W_]+', '', lines[i])
            new_lines.append(new_line)  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


# mybatis_xml_test_column
# XML字段判空条件查询
#  <if test="param.userId !=null and param.userId !='' ">
#     AND USER_ID = #{param.userId}
# </if>
class MybatisXmlTestColumnCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        # print(type(txt)) <class 'str'>
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            # 蛇形命名
            camle_style = snake_to_camel_style(lines[i])
            new_line = '<if test="param.' + camle_style + ' != null and param.' + camle_style + ' != \'\'">\n'
            new_line = new_line + '\tAND ' + lines[i] + ' = #{param.' + camle_style + '}\n</if>'
            new_lines.append(new_line)  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


# 单表更新XML
class MybatisXmlUpdateColumnCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        # print(type(txt)) <class 'str'>
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            # 蛇形命名
            camle_style = snake_to_camel_style(lines[i])
            new_line = '<if test="param.' + camle_style + ' != null and param.' + camle_style + ' != \'\'">\n'
            new_line = new_line + '\t ' + lines[i] + ' = #{param.' + camle_style + '},\n</if>'
            new_lines.append(new_line)  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


# 去除每行中间的空格
class RemoveBlankCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            new_lines.append("".join(lines[i].split()))  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


# 取出左边的空格
class RemoveLeftBlankCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            new_lines.append(lines[i].lstrip())  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)

# 取出右边的空格
class RemoveRightBlankCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            new_lines.append(lines[i].rstrip())  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)

# 取出左右两边的空格
class StripBlankCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            new_lines.append(lines[i].strip())  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


class RemoveBlankLineCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            if lines[i] == "":
                continue
            new_lines.append(lines[i])  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


class SnakeToCamelCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            if lines[i] == "":
                continue
            new_lines.append(snake_to_camel_style(lines[i]))  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


class CamelToSnakeCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            if lines[i] == "":
                continue
            new_lines.append(camel_to_snake_style(lines[i]))  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


# 用于测试Sublime Text API
# http://www.sublimetext.com/docs/api_reference.html
class TestSublimeApiCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        sublime.error_message("错误信息")

# 美化文本显示
# 适用于{k1=v1, k2=v2}
class BeautifyTextCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        new_lines = []
        txt = txt.strip()
        l = len(txt)

        kv_pairs = txt[1:l-1].split(",")
        new_lines = []
        for kv_pair in kv_pairs:
            new_lines.append(kv_pair)

        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)


# Maven-Gradle坐标转换工具
class GradleToMavenCommand(sublime_plugin.TextCommand):

    def run(self, edit):
        reg, txt = get_selcted(self.view)
        lines = str_to_lines(txt)
        new_lines = []
        for i in range(0, len(lines)):
            if lines[i] == "":
                continue
            gav_list = lines[i].split(":")
            group_id = "\n\t<groupId>" + gav_list[0] + "</groupId>\n"
            artifact_id = "\t<artifactId>" + gav_list[0] + "</artifactId>\n"
            version = "\t<version>" + gav_list[0] + "</version>\n"
            new_line = "<dependency>" + group_id + artifact_id + version + "</dependency>"
            new_lines.append(new_line)  # 直接重新赋值引用无效
        txt = lines_to_string(new_lines)
        self.view.replace(edit, reg, txt)



