

class Field:
    code: str          # 字段编码
    name: str          # 字段名称
    value: str         # 字段值
    type_literal: str  # 类型字面量
    description: str   # 字段描述信息
    extra_info: dict   # 附加信息

    def __init__(self, code, name, value, type_literal, description):
        self.code = code
        self.name = name
        self.value = value
        self.type_literal = type_literal
        self.description = description

    def to_string(self):
        return self.code + ":" + self.value

    def format(self):
        result = "# " + self.description + "\n" + self.code + ": " + self.type_literal
        print(result)