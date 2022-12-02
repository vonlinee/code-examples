package auto.freemaker;

import auto.config.BeanConstant;
import auto.config.BeanUtil;
import auto.config.TableColumnHandler;
import auto.pojo.TablePOJO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JpaUtil {

    public static void jpa(List<String> exceptTableList, String fileLevel, Map<String, Object> commentMap, String tableName, boolean createXml) throws Exception {
        List<TablePOJO> tableList = null;
        if (StringUtils.isNotBlank(tableName)) {
            tableList = TableColumnHandler.tableColumnType(tableName);
        } else {
            tableList = TableColumnHandler.tableColumnType();
            tableList = tableList.stream().filter(p -> !exceptTableList.contains(p.getTableName())).collect(Collectors.toList());
        }

        jpaBase(tableList, "bean.ftl", BeanConstant.JPA_IMPORTS_BEAN, "", "pojo", ".supervisvionplatform", commentMap);

        System.out.println("1-mapper");
        if (createXml) {
            jpaBase(tableList, "mapper2.ftl", null, "Mapper", "mapper", ".supervisvionplatform", commentMap);
            System.out.println("1.5-mapperXml");
            jpaBase(tableList, "mapper_xml.ftl", null, "Mapper", "mapper", ".xml", commentMap);
        } else {
            jpaBase(tableList, "mapper.ftl", null, "Mapper", "mapper", ".supervisvionplatform", commentMap);
        }

        /**
         * 可选生成模块
         */
        if (fileLevel.equals("service") || fileLevel.equals("controller")) {
            System.out.println("2-service");
            jpaBase(tableList, "service.ftl", BeanConstant.JPA_IMPORTS_SERVICE, "Service", "service", ".supervisvionplatform", commentMap);
            System.out.println("3-serviceImpl");
            jpaBase(tableList, "service_impl.ftl", BeanConstant.JPA_IMPORTS_SERVICE_IMPL, "ServiceImpl", "service//impl", ".supervisvionplatform", commentMap);
        }

        if (fileLevel.equals("controller")) {
            System.out.println("4-controller");
            jpaBase(tableList, "controller.ftl", BeanConstant.JPA_IMPORTS_CONTROLLER, "Controller", "controller", ".supervisvionplatform", commentMap);
        }


    }


    public static void jpaBase(List<TablePOJO> tableList, String ftlName, String beanConfig, String javaPostfix, String fileName, String filePostfix, Map<String, Object> commentMap) throws Exception {
        for (TablePOJO table : tableList) {
            String hump = BeanUtil.toHump(table.getTableName(), 1);
            String java = hump + javaPostfix;

            Map<String, Object> map = new HashMap<>();

            String classPath = BeanConstant.JPA_CLASS_PATH;
            String packageStr = "";
            if (classPath.contains("src/main/supervisvionplatform")) {
                classPath = classPath.split("src/main/java/")[1].replace("/", ".");
                packageStr = classPath.substring(0, classPath.length() - 1);
                classPath += fileName;
            } else {
                classPath = null;
            }

            map.put("classPath", classPath);
            map.put("Package", packageStr);
            map.put("imports", beanConfig);
            map.put("className", table.getTableName());
            map.put("tableName", table.getTableName());
            map.put("fileName", java);
            map.put("newMember", table.getColumnCountList());
            map.put("underline", BeanConstant.JPA_FIELD_UNDERLINE);
            map.put("T", hump);
            map.put("Service", hump + "Service");
            map.put("Repository", hump + "Repository");
            map.put("Controller", toLowerCaseFirstOne(hump));
            map.put("Mapper", hump + "Mapper");
            map.put("mapping", javaPostfix);
            map.putAll(commentMap);

            base(map, ftlName, fileName, java + filePostfix);
        }
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 创建freeMaker配置实例
     *
     * @param dataMap
     * @param ftlName
     * @param dir
     * @param newFileName
     */
    public static void base(Map<String, Object> dataMap, String ftlName, String dir, String newFileName) {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(BeanConstant.JPA_TEMPLATE_PATH));
            // step3 创建数据模型
            // step4 加载模版文件
            Template template = configuration.getTemplate(ftlName);
            // step5 生成数据
            File newDir = new File(BeanConstant.JPA_CLASS_PATH + dir);
            File newFile = new File(newDir, newFileName);
            if (!newDir.exists()) {
                newDir.mkdirs();
                newFile.createNewFile();
            }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile)));
            // step6 输出文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
