# PrettyTable  、 prettytables 
# tabulate 不支持颜色打印
# openstack的cliff

# https://stackoverflow.com/questions/5909873/how-can-i-pretty-print-ascii-tables-with-python

# pip3 install PrettyTable

from prettytable import PrettyTable

# 标题
x = PrettyTable(["City name", "Area", "Population", "Annual Rainfall"])
# 对齐方式
x.align["City name"] = "l" # Left align city names
# 边距宽度
x.padding_width = 1 # One space between column edges and contents (default)

x.add_row(["Adelaide",1295, 1158259, 600.5])
x.add_row(["Brisbane",5905, 1857594, 1146.4])
x.add_row(["Darwin", 112, 120900, 1714.7])
x.add_row(["Hobart", 1357, 205556, 619.5])
x.add_row(["Sydney", 2058, 4336374, 1214.8])
x.add_row(["Melbourne", 1566, 3806092, 646.9])
x.add_row(["Perth", 5386, 1554769, 869.4])

print(x)







