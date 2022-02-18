# -*- coding: utf-8 -*-
# 默认待处理文本由文本文件提供

import chardet  # 编码检测
import xlrd  # 读Excel
import xlwt  # 写Excel
import random  # 随机数
import json

def detect_qt_environment():
    pass


EXCEL_TEMPLATE_DIR = "./ExcelTemplate/"
RELATIVE_OUTPUT_DIR = "./output/"
# 默认处理的文本文件
INPUT_TXT_FILE_PATH = "./input.txt"
OUTPUT_TXT_FILE_PATH = "./output.txt"

# 名称(1)	     值(2)	        类型(3)
# 编码  dlrOrderCode   String
def read_excel(excel_path, api_method_name="", txt_path=None) -> str:
    excel_data = xlrd.open_workbook(excel_path)
    table = excel_data.sheet_by_index(0)
    column_count = table.ncols
    row_count = table.nrows  # 表的行数
    l = []
    for i in range(0, row_count):  # 第一行为表头，不记录,0表示第一行
        row = table.row_values(i, start_colx=0, end_colx=None)
        graphql_column_str = s1 + "'" + row[1] + "', " + "'" + row[0] + "'"+ s2
        print(graphql_column_str)
    return "".join(l)


def make_graphql_field(template_path: str, field_type: str, field_name: str, output_dir: str=None) -> None:
    """[summary]
    输出GraphQL的Field Schema文本
    Args:
        template_path (str): [Excel模板文件路径]
        field_type (str): [GraphQL Field类型：input， type，enum等]
        field_name (str): [GraphQL Field名称]
        output_dir (RELATIVE_OUTPUT_DIR): [description]
    """
    if output_dir is None:
        output_dir = RELATIVE_OUTPUT_DIR # "./output/"
    content = field_type + " " + field_name + " {" + read_excel(template_path) + "\n}"
    output_path = output_dir + "1.txt"
    with open(output_path, "w", encoding= 'utf-8') as f:
        f.write(content)
        f.flush()

# USER_NAME -> userName
def snake_to_camel_style(snake_str: str, upper_first=False) -> str:
    """[summary]
    Args:
        snake_str (str): [snake_str]
        upper_first (bool, optional): 是否首字母大写，默认True
    Returns:
        str: [description]
    """
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
    """[summary]
    camel_to_snake_style
    Args:
        camel_str (str): [AxxBxxCxx]
        lower_all (bool, optional): [description]. Defaults to False.

    Returns:
        str: [description]
    """
    import re
    snake_case = re.sub(r"(?P<key>[A-Z])", r"_\g<key>", camel_str)
    result = snake_case.strip('_')
    print("".join(result))
    if lower_all:
        return result.lower()
    return result.upper()

def pascal_case_to_snake_case(camel_case: str):
    import re
    snake_case = re.sub(r"(?P<key>[A-Z])", r"_\g<key>", camel_case)
    return snake_case.lower().strip('_')

def snake_to_pascal_case(snake_case: str, upper_first=False):
    """蛇形转大驼峰（帕斯卡）"""
    words = snake_case.split('_')
    result = ''.join(word.title() for word in words)
    if upper_first:
        return result[0].upper() + result[1:]
    return result[0].lower() + result[1:]

def generate_dubbo_xml_config(interfaceName: str, serviceId: str) -> str:
    """[summary]

    Args:
        interfaceName (str): [description]
        serviceId (str): [description]

    Returns:
        str: [description]
    """
    return '<dubbo:service interface="{interfaceName}" ref="{serviceId}"/>'.format(interfaceName=interfaceName, serviceId=serviceId)


def method(param_list: dict, return_type: str) -> str:
    pass


def create_graphqls_content():
    input_prefix = "Input"
    suffix = "Ext"
    result_prefix = "ListResult"
    default_template_dir = "./ExcelTemplate/"
    make_graphql_field(default_template_dir + r"字段描述模板.xlsx", "type", "VeMktActInfoActMode")


def batch_snake_to_camel_style(
    input_source=1,
    output_target= 0,
    *args, 
    input_file_path=INPUT_TXT_FILE_PATH,
    output_file_path=OUTPUT_TXT_FILE_PATH):
    if input_source == 0:  # 参数输入
        result_list = []
        for arg in args:
            result_list.append(snake_to_camel_style(arg))
        if output_target == 0:
            for i in result_list:
                print(i)
        if output_target == 1:
            with open(output_file_path, 'w') as f:
                f.writelines(result_list)
    elif input_source == 1:
        result_list = []
        with open(input_file_path, 'r') as f:
            lines = f.readlines()
            for line in lines:
                s = str(line).strip()
                result_list.append(snake_to_camel_style(s))
        if output_target == 0:
            for i in result_list:
                print(i)
        if output_target == 1:
            with open(output_file_path, 'w') as f:
                f.writelines(result_list)


def batch_camel_to_snake_style(
    input_source=0, 
    output_target= 0, 
    *args, 
    input_file_path=INPUT_TXT_FILE_PATH,
    output_file_path=OUTPUT_TXT_FILE_PATH):
    if input_source == 0:   # 函数传参
        result_list = []
        for arg in args:
            result_list.append(camel_to_snake_style(arg))
        if output_target == 0:
            for i in result_list:
                print(i)
        if output_target == 1:
            with open(output_file_path, 'w') as f:
                f.writelines(result_list)
    elif input_source == 1: # 文本文件
        result_list = []
        with open(input_file_path, 'r') as f:
            lines = f.readlines()
            for line in lines:
                s = str(line).strip()
                result_list.append(camel_to_snake_style(s))
        if output_target == 0:
            for i in result_list:
                print(str(i).upper())
        if output_target == 1:
            with open(output_file_path, 'w') as f:
                f.writelines(result_list)


def remove_blank_lines(
    input_file: str = INPUT_TXT_FILE_PATH,
    output_file: str = OUTPUT_TXT_FILE_PATH) -> None:
    """[remove_blank_lines]
    Args:
        input_file (str, optional): [description]. Defaults to './input.txt'.
        output_file (str, optional): [description]. Defaults to './output.txt'.
    """
    new_lines = []
    with open(input_file, 'r') as input:
        line = input.readline()
        if not line.replace(' ', '') == '':
             new_lines.append(line)
    with open(output_file, 'w') as output:
        output.writelines(line for line in new_lines)
    


def graphql_type_to_excel(graphqls_file: str) -> None:
    """[summary]
    将GraphQLS文件的字段保存入Excel
    Args:
        graphqls_file (str): [txt]
    """
    with open(graphqls_file) as graphqls_file:
        lines = graphqls_file.readlines()
        line_no = 0 # 行号
        column_list = []
        column_info = {}
        flag = 1
        for line in lines:
            line = str(line, 'utf-8')
            line_no = line_no + 1
            if line_no == 1:
                strings = line.replace('\n', '').split(" ")
            else: # 去除第一行
                if line_no % 2 == 0: # 字段描述
                    line = line.strip().replace('\n', '')
                    len = len(line)
                    column_info['column_description'] = line[1:len]
                    flag = 2
                if line_no % 2 == 1: # 字段名：字段类型
                    column = line.strip().split(':')
                    column_info['column_name'] = column[0]
                    column_info['column_type'] = column[1]
                    flag = 3
            if flag == 3:
                column_list.append(column_info)
                flag = 1
                column_info.clear() # 清空字段信息表
        for item in column_list:
            print(item['column_description'] + "-" + item['column_name'] + "-" + item['column_type'])     


def format_date_column(column_name: str) -> str:
    """[format date]
    Args:
        column_name (str): [db_column_name]
    Returns:
        str: [description]
    """
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


def split_line_to_nline(
    input_file: str = INPUT_TXT_FILE_PATH,
    delimeter=',') -> None:
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


def merge_nline_to_line(
    input_file: str = INPUT_TXT_FILE_PATH,
    delimeter=',') -> None:
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


def lines_to_empty_json(input_file: str = INPUT_TXT_FILE_PATH):
    lines = None
    with open(input_file, 'r') as f:
        lines = f.readlines()
    empty_key_json = {}
    for line in lines:
        empty_key_json[line.strip()] = ""
    json_str = json.dumps(empty_key_json, sort_keys=True, indent=4, separators=(',', ': '))
    print(json_str)


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

# 去除每行的首位空格
def batch_strip(input_file: str = INPUT_TXT_FILE_PATH):
    lines = None
    with open(input_file, 'r') as f:
        lines = f.readlines()
        for line in lines:
            print(line.strip())


def batch_aligen(aligenment: str= 'L')-> None:
    """
    文本对齐，默认左对齐
    """
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


s = '<if test="${columnValue} !=null and param.taskId !='' ">'\
'    and T.TASK_ID = #{param.taskId}' \
'</if>'



# if __name__ == '__main__':
#     batch_snake_to_camel_style(1)

with open("input.txt", 'r') as f:
    result_list = []
    lines = f.readlines()
    for line in lines:
        s = str(line).strip()
        camel_style = snake_to_camel_style(s)
        camel_style = 'param.' + camel_style 
        print('<if test="' + camel_style + ' !=null and ' + camel_style + ' !='' ">')
        print('AND T.' + s + ' = #{ ' + camel_style + ' }")'
        print('</if>')
        result_list.append()