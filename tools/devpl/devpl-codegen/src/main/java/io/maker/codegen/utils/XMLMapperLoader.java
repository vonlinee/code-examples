package io.maker.codegen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * 启动项目自动加载 XML
 * <p>
 * https://blog.csdn.net/liaominyu/article/details/108263435
 * </p>
 *
 * 此类主要将项目启动时xml文件加载到 sqlSessionFactory 中的 Configuration缓存清除,再扫描本地xml加入到该缓存中,
 * 从而达到,xml没有经过编译也可以实现实时更新(网上也有其他的加载的编译后的xml,也就是build路径加的xml,
 * 但如果项目没有编译,xml无法实时更新,注意scanMapperXml()方法的路径你要换成自己对应的XML文件路径
 */
public class XMLMapperLoader {

    private static final Logger logger = LoggerFactory.getLogger(XMLMapperLoader.class);
    private final SqlSessionFactory sqlSessionFactory;
    private Resource[] mapperLocations;
    private String packageSearchPath = "/mapper";

    public XMLMapperLoader(SqlSessionFactory sqlSessionFactory, String packageSearchPath) {
        this.sqlSessionFactory = sqlSessionFactory;
        if (packageSearchPath != null && !"".equals(packageSearchPath)) {
            this.packageSearchPath = packageSearchPath;
        }
        startThreadListener();
    }

    /**
     * 如果只需要执行一次可以将监听线程重写或者删除XMLMapperLoader ().startThreadListener()
     */
    public void startThreadListener() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        //每10秒执行一次
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                readMapperXml();
            }
        }, 0, 10, TimeUnit.SECONDS);
        readMapperXml();
    }

    public String readMapperXml() {
        try {
            Configuration configuration = sqlSessionFactory.getConfiguration();
            // 扫描文件
            this.scanMapperXml();
            // 清空configuration map的数据
            this.removeConfig(configuration);
            // 将xml 重新加载
            for (Resource configLocation : mapperLocations) {
                if ("TestMapper.xml".equals(configLocation.getFilename())) {
                    try (InputStream is = configLocation.getInputStream()) {
                        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(is, configuration, configLocation.toString(), configuration.getSqlFragments());
                        xmlMapperBuilder.parse();
                        logger.debug("mapper文件[" + configLocation.getFilename() + "]缓存加载成功");
                    } catch (IOException e) {
                        logger.debug("mapper文件[" + configLocation.getFilename() + "]不存在或内容格式不对");
                    }
                }
            }
            return "refresh mybatis xml succssful ";
        } catch (Exception e) {
            return "refresh mybatis xml fail";
        }
    }

    /**
     * 扫描xml文件所在的路径
     */
    private void scanMapperXml() {
        //根据自己项目的实际路径查替换, 最终是找到非编译的 xml所在的文件夹路径
        String fileUrl = this.getClass()
                .getResource(packageSearchPath)
                .getPath()
                .replace("/main", "")
                .replace("build", "src/main");
        File file = new File(fileUrl);
        File[] matchingFiles = file.listFiles();
        if (matchingFiles == null || matchingFiles.length == 0) {
            return;
        }
        Set<Resource> result = new LinkedHashSet<>(matchingFiles.length);
        for (File files : matchingFiles) {
            result.add(new FileSystemResource(files));
        }
        this.mapperLocations = result.toArray(new Resource[0]);
    }

    /**
     * 清空Configuration中几个重要的缓存
     *
     * @param configuration MyBatis配置类
     * @throws Exception
     */
    private void removeConfig(Configuration configuration) throws Exception {
        Class<?> classConfig = configuration.getClass();
        clearMap(classConfig, configuration, "mappedStatements");
        clearMap(classConfig, configuration, "caches");
        clearMap(classConfig, configuration, "resultMaps");
        clearMap(classConfig, configuration, "parameterMaps");
        clearMap(classConfig, configuration, "keyGenerators");
        clearMap(classConfig, configuration, "sqlFragments");
        clearSet(classConfig, configuration, "loadedResources");
    }

    /**
     * @param classConfig   配置类
     * @param configuration MyBatis配置类
     * @param fieldName     字段名
     * @throws Exception
     */
    private void clearMap(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
        Field field;
        if (configuration.getClass().getName().equals("com.baomidou.mybatisplus.core.MybatisConfiguration")) {
            field = classConfig.getSuperclass().getDeclaredField(fieldName);
        } else {
            field = classConfig.getDeclaredField(fieldName);
        }
        field.setAccessible(true);
        @SuppressWarnings("rawuse")
        Map mapConfig = (Map) field.get(configuration);
        mapConfig.clear();
    }

    private void clearSet(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
        Field field = null;
        if (configuration.getClass().getName().equals("com.baomidou.mybatisplus.core.MybatisConfiguration")) {
            field = classConfig.getSuperclass().getDeclaredField(fieldName);
        } else {
            field = classConfig.getDeclaredField(fieldName);
        }
        field.setAccessible(true);
        Set setConfig = (Set) field.get(configuration);
        setConfig.clear();
    }
}