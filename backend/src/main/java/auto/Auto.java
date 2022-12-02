package auto;

import auto.config.BeanConstant;
import auto.config.DatabaseConfig;
import auto.freemaker.JpaUtil;

import java.text.SimpleDateFormat;
import java.util.*;

public class Auto {

    public static void main(String[] args) {
        try {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("driverClassName", "com.mysql.cj.jdbc.Driver");
            configMap.put("url", "jdbc:mysql://192.168.129.53:3306/test?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
            configMap.put("username", "root");
            configMap.put("password", "123456");
            DatabaseConfig.setDruidMysqlSource(configMap);

            // 固定生成的包路径
            String packagePath = "com/projectName/java/";
            BeanConstant.JPA_CLASS_PATH += packagePath;

            // 生成代码所带的作者、版本、创建时间注释
            Map<String, Object> commentMap = new HashMap<>();
            commentMap.put("author", "xxx");
            commentMap.put("version", "V1.0");
            commentMap.put("creatortime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            // 除却该list中的表名,其余全部生成
            List<String> exceptTableList = new ArrayList<>();
            exceptTableList.add("xx");
            // 最高优先级，生成单个对应表的数据
            String tableName = "city";
            // 生成等级，controller-三层，service-两层，mapper-仅生成mapper与bean
            String fileLevel = "controller";
            System.out.println("---------开始生成-----------");
            // createXml 创建mapper.xml-true 不创建-false
            JpaUtil.jpa(exceptTableList, fileLevel, commentMap, tableName, false);
            System.out.println("---------生成完成-----------");
        } catch (Exception e) {
            System.out.println("自动生成异常");
        }
    }
}
