package com.baomidou.mybatisplus.test.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.config.rules.PropertyInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
/**
 * <p>
 * 代码生成器演示
 * </p>
 *
 * @author hubin
 * @since 2016-12-01
 */
public class MysqlGenerator extends GeneratorTest {
	
	//输出路径
	private static String OutputDir = "D:\\BF\\Desktop\\";
	//作者
	private static String Author = "mengs";
	//数据库用户
	// private static String Username = "kfuser";
	private static String Username = "root";
	//数据库密码
	//private static String Password = "wa!#ygc520!";
	// private static String Password = "Kfuser@2020";
	
	private static String Password = "123456";
	
	
	//数据库连接
	//private static String Url = "jdbc:mysql://172.26.165.30:3306/productcenter?characterEncoding=utf-8&useUnicode=true&useSSL=false";
	//private static String Url = "jdbc:mysql://172.26.165.27:3306/finacecenter?characterEncoding=utf-8&useUnicode=true&useSSL=false";
	//private static String Url = "jdbc:mysql://172.26.165.27:3306/aftersalecenter?characterEncoding=utf-8&useUnicode=true&useSSL=false";
	//private static String Url = "jdbc:mysql://172.26.165.30:3306/ordercenter?characterEncoding=utf-8&useUnicode=true&useSSL=false";
	//private static String Url = "jdbc:mysql://172.26.165.30:3306/scmcenter?characterEncoding=utf-8&useUnicode=true&useSSL=false";
	
	// private static String Url = "jdbc:mysql://172.26.239.244:3306/scmcenter?characterEncoding=utf-8&useUnicode=true&useSSL=false";
	private static String Url = "jdbc:mysql://localhost:3306/information_schema?useUnicode=true&characterEncoding=UTF-8&useSSL=false&jdbcCompliantTruncation=false&zeroDateTimeBehavior=CONVERT_TO_NULL&useAffectedRows=true&serverTimezone=GMT%2B8";
	
	
	//业务前缀 决定生成的文件  busicen.prc\ly.mp.busicen.prc
	private static String busicen = "busicen";
	//包前缀 决定生成的包 
	private static String parent = "com.ly.mp.busicen";
	//包配置名 中心简称 如 prc
	private static String ModuleName = "scc";
	//表前缀 t_中心简称 如 t_prc
	private static String[] TablePrefix = {"t_scc"};
	//需要生成的表名
	private static String[] Include = {"COLUMNS", "TABLES"};
	//是否生成查询方法 0或1
	private static int queryFalg = 1;
	//是否生成保存方法 0或1
	private static int mutationFalg = 1;
	
    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        //int result = scanner();
        int result = 1;
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
            // 全局配置
            new GlobalConfig()
                .setOutputDir(OutputDir)//输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(false)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                //.setKotlin(true) 是否生成 kotlin 代码
                .setAuthor(Author)
            // 自定义文件命名，注意 %s 会自动填充表实体属性！
            // .setEntityName("%sEntity");
            // .setMapperName("%sDao")
            // .setXmlName("%sDao")
             .setServiceName("I%sBiz")
            .setServiceImplName("%sBiz")
             .setControllerName("%sService")
             .setGraphqlName("%s.0")
             .setGraphql1Name("%s.1")
             .setModelName("%sModel")
             .setGraphqlQueryName("%sQuery")
             .setGraphqlMutationName("%sMutation")
             .setExcelQueryName("业务中台_微服务清单_%s_查询API")
             .setExcelMutationName("业务中台_微服务清单_%s_保存API")
        ).setDataSource(
            // 数据源配置
            new DataSourceConfig()
                .setDbType(DbType.MYSQL)// 数据库类型
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    @Override
                    public PropertyInfo processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                        //    return DbColumnType.BOOLEAN;
                        // }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername(Username)
                .setPassword(Password)
                .setUrl(Url)
        ).setStrategy(
            // 策略配置
            new StrategyConfig()
                // .setCapitalMode(true)// 全局大写命名
                // .setDbColumnUnderline(true)//全局下划线命名
                .setTablePrefix(TablePrefix)// 此处可以修改为您的表前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setInclude(Include) // 需要生成的表
                //.setExclude(new String[]{}) // 排除生成的表
                		
                // 自定义实体父类
                // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                // 自定义实体，公共字段
                //.setSuperEntityColumns(new String[]{"creator"})
                //公共字段填充规则
                .setTableFillList(tableFillList)
                .setSuperModelClass("com.coxautodev.graphql.tools.GraphQLResolver")
            // 自定义 mapper 父类
            // .setSuperMapperClass("com.baomidou.demo.TestMapper")
            // 自定义 service 父类
            // .setSuperServiceClass("com.baomidou.demo.TestService")
            // 自定义 service 实现类父类
            // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
            // 自定义 controller 父类
            // .setSuperControllerClass("com.baomidou.demo.TestController")
            // 【实体】是否生成字段常量（默认 false）
            // public static final String ID = "test_id";
            // .setEntityColumnConstant(true)
            // 【实体】是否为构建者模型（默认 false）
            // public User setName(String name) {this.name = name; return this;}
            // .setEntityBuilderModel(true)
            // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
            // .setEntityLombokModel(true)
            // Boolean类型字段是否移除is前缀处理
            // .setEntityBooleanColumnRemoveIsPrefix(true)
             .setRestControllerStyle(true)
            // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
            // 包配置
            new PackageConfig()
            	.setBusicen(busicen)
                .setModuleName(ModuleName)
                .setParent(parent)// 自定义包路径
                .setService("ibiz")
                .setServiceImpl("biz")
                .setMapper("idal.mapper")
                .setController("service")// 这里是控制器包名，默认 web
                .setModel("resolver.model")
                .setEntity("entities")

        ).setCfg(
            // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值  模板使用 cfg[abc]
            new InjectionConfig() {
                @Override
                public void initMap() {
                    Map<String, Object> map = new HashMap<>();
                    map.put("queryFalg", queryFalg);
                    map.put("mutationFalg", mutationFalg);
                    this.setMap(map);
                }
            }
//            .setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(
//                "/templates/mapper.xml" + ((1 == result) ? ".ftl" : ".vm")) {
//                // 自定义输出文件目录
//                @Override
//                public String outputFile(TableInfo tableInfo) {
//                    return OutputDir + tableInfo.getEntityName() + ".xml";
//                }
//            }))
        ).setTemplate(
            // 关闭默认 xml 生成，调整生成 至 根目录
            new TemplateConfig()
            //.setXml(null)
            // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
            // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
            // .setController("...");
            // .setEntity("...");
            // .setMapper("...");
            // .setXml("...");
            // .setService("...");
            // .setServiceImpl("...");
        );
        // 执行生成
        if (1 == result) {
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        }
        mpg.execute();
    }
}
