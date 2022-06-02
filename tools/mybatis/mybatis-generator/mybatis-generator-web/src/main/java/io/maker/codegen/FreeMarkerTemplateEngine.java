package io.maker.codegen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * http://www.freemarker.net/
 * http://freemarker.foofun.cn/dgui_quickstart_template.html
 *
 *
 */
public class FreeMarkerTemplateEngine {

	public static void main(String[] args) throws IOException, TemplateException {
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是FreeMarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());

		// 第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\ly-wangliang\\Desktop\\code-samples\\tools\\mybatis\\mybatis-generator\\mybatis-generator-web\\src\\main\\resources\\templates\\engine\\freemarker\\"));

		// 第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("utf-8");

		// 第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("javaclass.ftl");

		// 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map<String, Object> dataModel = new HashMap<>();
		// 向数据集中添加数据

		dataModel.put("javaType", "class");
		dataModel.put("javaVisiablity", "");
		dataModel.put("typeName", "Main");

		Map<String, Object> fieldList = new HashMap<>();
		fieldList.put("size", 10);

		List<Map<String, Object>> list = new ArrayList<>();
		list.add(new HashMap<>());
		list.add(new HashMap<>());
		list.add(new HashMap<>());
		list.add(new HashMap<>());
		fieldList.put("list", list);
		dataModel.put("fieldList", fieldList);

		// 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		Writer out = new FileWriter(new File("D:/Temp/hello.java"));

		// 第七步：调用模板对象的process方法输出文件。
		template.process(dataModel, out);

		// 第八步：关闭流。
		out.close();
	}
}
