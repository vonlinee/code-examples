

class StringBuilder:
    string: str

# 对齐文本
class StringAligener:
    pass

strs = ["flower122", "flow", "flight"]

"""
min_len_str = min(strs, key=len)    直接找出最小最大的字符串
max_len_str = max(strs, key=len)
"""
def find_max_length(str_list: list) -> None:
    len_str = len(str_list[0])
    min_num_index = 0   # 最小值的下标
    stack = [str_list[0]]   # 利用栈来找出最短的字符串
    for index, string in enumerate(str_list):
        if len(string) < len_str:
            stack.pop()
            len_str = len(string)
            min_num_index = index # 知道最短字符对应的下标后，也可以自己找出最短字符
            stack.append(string)
 



print(max_len_str)














