import xlrd  # 读Excel
import xlwt  # 写Excel
import chardet # 编码探测

# 根据Excel生成SQL语句

def generate_insert_statement(excel_path) -> None:
    excel_data = xlrd.open_workbook(excel_path)
    table = excel_data.sheet_by_index(0)
    column_count = table.ncols
    row_count = table.nrows  # 表的行数
    print(column_count) 
    column_description = []
    column_name = []
    column_value = []
    column_type = []
    for i in range(1, row_count):  # 第一行为表头,不记录,0表示第一行
        row = table.row_values(i, start_colx=0, end_colx=None)
        column_description.append("/*" + row[0] + "*/")
        column_name.append(row[1])
        column_value.append(row[2])
        column_type.append(row[3])
    insert_columns = "(" + ",\n".join(column_name) + ")"
    insert_values = ",\n".join(column_value) + ")"
    print(insert_values)


if __name__ == '__main__':
    generate_insert_statement('../ExcelTemplate/Template2.xlsx')
    



























