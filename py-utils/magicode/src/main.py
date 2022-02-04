
# pip install jinja2
# http://docs.jinkan.org/docs/jinja2/

from jinja2 import Template
from jinja2 import Environment, PackageLoader

# 参数1：模块名，参数2：模板路径

global_env = {
	"default": 
}


def init_environment() -> None
	module_name = "magicode"
	template_relative_path = "templates/jinja2"
	try:
		loader = PackageLoader(module_name, template_relative_path)
		global_env['default'] = Environment(loader=loader)
	except Exception as e:
		raise e


print(env)



template = Template('Hello {{ name }}!')



# Jinja2 内部使用 unicode 并且返回值也是 unicode 字符串
# 在有字典或关键字参数时调用 扩充模板。字典或关键字参数会被传递到模板，即模板“上下文”
result = template.render(name='John Doe')

print(result)












