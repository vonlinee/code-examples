package io.maker.codegen.mbp.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.Converter;

import io.maker.base.utils.CaseFormat;
import io.maker.base.utils.Maps;
import io.maker.codegen.mbp.config.ConstVal;
import io.maker.codegen.mbp.config.GlobalConfig;
import io.maker.codegen.mbp.config.OutputFile;
import io.maker.codegen.mbp.config.StrategyConfig;
import io.maker.codegen.mbp.config.TemplateConfig;
import io.maker.codegen.mbp.config.builder.ConfigBuilder;
import io.maker.codegen.mbp.config.po.TableField;
import io.maker.codegen.mbp.config.po.TableInfo;
import io.maker.codegen.mbp.fill.XMLTag;
import io.maker.codegen.mbp.util.FileUtils;
import io.maker.codegen.mbp.util.MapperUtils;
import io.maker.codegen.mbp.util.RuntimeUtils;

/**
 * 模板引擎抽象类，提供生成代码的模板方法
 */
public abstract class AbstractTemplateEngine {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;

    /**
     * 模板引擎初始化
     */
    public abstract AbstractTemplateEngine init(ConfigBuilder configBuilder);

    /**
     * 输出自定义模板文件
     * @param customFile 自定义配置模板文件信息
     * @param tableInfo  表信息
     * @param objectMap  渲染数据
     * @since 3.5.1
     */
    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String otherPath = getPathInfo(OutputFile.other);
        customFile.forEach((key, value) -> {
            String fileName = String.format((otherPath + File.separator + entityName + File.separator + "%s"), key);
            outputFile(new File(fileName), objectMap, value, getConfigBuilder().getInjectionConfig().isFileOverride());
        });
    }

    /**
     * 输出实体文件
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputEntity(TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = getPathInfo(OutputFile.entity);
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
            getTemplateFilePath(template -> template.getEntity(getConfigBuilder().getGlobalConfig()
                                                                                 .isKotlin())).ifPresent((entity) -> {
                String entityFile = String.format((entityPath + File.separator + "%s" + suffixJavaOrKt()), entityName);
                outputFile(new File(entityFile), objectMap, entity, getConfigBuilder().getStrategyConfig().entity()
                                                                                      .isFileOverride());
            });
        }
    }

    /**
     * 输出Mapper文件(含xml)
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputMapper(TableInfo tableInfo, Map<String, Object> objectMap) {
        logger.info("开始输出Mapper.java");

        // MpMapper.java
        String entityName = tableInfo.getEntityName();
        String mapperPath = getPathInfo(OutputFile.mapper);
        if (StringUtils.isNotBlank(tableInfo.getMapperName()) && StringUtils.isNotBlank(mapperPath)) {
            getTemplateFilePath(TemplateConfig::getMapper).ifPresent(mapper -> {
                String mapperFile = String.format((mapperPath + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);

                logger.info("模板文件路径:{}, 输出到文件: {}", mapper, mapperFile);
                outputFile(new File(mapperFile), objectMap, mapper, getConfigBuilder().getStrategyConfig().mapper()
                                                                                      .isFileOverride());
            });
        }
        // MpMapper.xml
        logger.info("开始输出Mapper.xml");
        String xmlPath = getPathInfo(OutputFile.xml);
        if (StringUtils.isNotBlank(tableInfo.getXmlName()) && StringUtils.isNotBlank(xmlPath)) {
            getTemplateFilePath(TemplateConfig::getXml).ifPresent(xml -> {
                String xmlFile = String.format((xmlPath + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                logger.info("模板文件路径:{}, 输出到文件: {}", xml, xmlFile);
                //增加默认的CRUD查询sql语句
                addDefaultMapperXmlCRUDTag(objectMap);

                ConfigBuilder configBuilder = getConfigBuilder();
                outputFile(new File(xmlFile), objectMap, xml, configBuilder.getStrategyConfig().mapper()
                                                                           .isFileOverride());
            });
        }
    }

    /**
     * 增加默认的增删改查语句
     * @param objectMap
     */
    private void addDefaultMapperXmlCRUDTag(Map<String, Object> objectMap) {
        objectMap.put("generateDefaultCrudXmlSQL", true);
        List<XMLTag> list = new ArrayList<>();
        objectMap.put("xmlCrudTags", list);

        TableInfo tableInfo = Maps.getValue(objectMap, "table");

        //Insert
        XMLTag insertXmlTag = createMapperXmlTag("insert", "AAAA");
        insertXmlTag.addAttribute("id", "");
        insertXmlTag.addAttribute("parameterType", "map");
        insertXmlTag.addAttribute("resultType", "int");
        insertXmlTag.setContent(MapperUtils.insertTag(tableInfo));
        list.add(insertXmlTag);

        //TODO 批量新增

        //Update
        XMLTag updateXmlTag = createMapperXmlTag("update", "AAAA");
        updateXmlTag.addAttribute("id", "");
        updateXmlTag.addAttribute("parameterType", "map");
        updateXmlTag.addAttribute("resultType", "int");
        updateXmlTag.setContent(MapperUtils.updateTag(tableInfo));
        list.add(updateXmlTag);

        //Select
        XMLTag selectXmlTag = createMapperXmlTag("select", "AAAA");
        selectXmlTag.addAttribute("id", "");
        selectXmlTag.addAttribute("parameterType", "map");
        selectXmlTag.addAttribute("resultType", "map");
        selectXmlTag.setContent(MapperUtils.selectTag(tableInfo));
        list.add(selectXmlTag);
    }

    private XMLTag createMapperXmlTag(String name, String id) {
        XMLTag xmlTag = new XMLTag();
        xmlTag.setName(name);
        return xmlTag;
    }

    /**
     * 输出service文件
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputService(TableInfo tableInfo, Map<String, Object> objectMap) {
        // IMpService.java
        String entityName = tableInfo.getEntityName();
        String servicePath = getPathInfo(OutputFile.service);
        if (StringUtils.isNotBlank(tableInfo.getServiceName()) && StringUtils.isNotBlank(servicePath)) {
            getTemplateFilePath(TemplateConfig::getService).ifPresent(service -> {
                String serviceFile = String.format((servicePath + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                outputFile(new File(serviceFile), objectMap, service, getConfigBuilder().getStrategyConfig().service()
                                                                                        .isFileOverride());
            });
        }
        // MpServiceImpl.java
        String serviceImplPath = getPathInfo(OutputFile.serviceImpl);
        if (StringUtils.isNotBlank(tableInfo.getServiceImplName()) && StringUtils.isNotBlank(serviceImplPath)) {
            getTemplateFilePath(TemplateConfig::getServiceImpl).ifPresent(serviceImpl -> {
                String implFile = String.format((serviceImplPath + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                outputFile(new File(implFile), objectMap, serviceImpl, getConfigBuilder().getStrategyConfig().service()
                                                                                         .isFileOverride());
            });
        }
    }

    /**
     * 输出controller文件
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputController(TableInfo tableInfo, Map<String, Object> objectMap) {
        // MpController.java
        String controllerPath = getPathInfo(OutputFile.controller);
        if (StringUtils.isNotBlank(tableInfo.getControllerName()) && StringUtils.isNotBlank(controllerPath)) {
            getTemplateFilePath(TemplateConfig::getController).ifPresent(controller -> {
                String entityName = tableInfo.getEntityName();
                String controllerFile = String.format((controllerPath + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                outputFile(new File(controllerFile), objectMap, controller, getConfigBuilder().getStrategyConfig()
                                                                                              .controller()
                                                                                              .isFileOverride());
            });
        }
    }

    /**
     * 输出文件（3.5.3版本会删除此方法）
     * @param file         文件
     * @param objectMap    渲染信息
     * @param templatePath 模板路径
     * @since 3.5.0
     */
    @Deprecated
    protected void outputFile(File file, Map<String, Object> objectMap, String templatePath) {
        outputFile(file, objectMap, templatePath, false);
    }

    /**
     * 输出文件
     * @param file         文件
     * @param objectMap    渲染信息
     * @param templatePath 模板路径
     * @param fileOverride 是否覆盖已有文件
     * @since 3.5.2
     */
    protected void outputFile(File file, Map<String, Object> objectMap, String templatePath, boolean fileOverride) {
        logger.info("写入数据到文件{}", file);
        if (isCreate(file, fileOverride)) {
            try {
                // 全局判断【默认】
                boolean exist = file.exists();
                if (!exist) {
                    FileUtils.forceMkdir(file.getParentFile());
                }
                write(objectMap, templatePath, file);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * 获取模板路径
     * @param function function
     * @return 模板路径
     * @since 3.5.0
     */

    protected Optional<String> getTemplateFilePath(Function<TemplateConfig, String> function) {
        TemplateConfig templateConfig = getConfigBuilder().getTemplateConfig();
        String filePath = function.apply(templateConfig);
        if (StringUtils.isNotBlank(filePath)) {
            return Optional.of(templateFilePath(filePath));
        }
        return Optional.empty();
    }

    /**
     * 获取路径信息
     * @param outputFile 输出文件
     * @return 路径信息
     */
    @Nullable
    protected String getPathInfo(OutputFile outputFile) {
        return getConfigBuilder().getPathInfo().get(outputFile);
    }

    /**
     * 批量输出 java xml 文件
     */
    public AbstractTemplateEngine batchOutput() {
        try {
            ConfigBuilder config = this.getConfigBuilder();
            logger.info("开始加载数据库表字段信息");
            List<TableInfo> tableInfoList = config.getTableInfoList();
            logger.info("加载数据库表个数：{}", tableInfoList.size());
            tableInfoList.forEach(tableInfo -> {
                System.out.println(tableInfo.getName());
            });
            tableInfoList.forEach(tableInfo -> {
                Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
                Optional.ofNullable(config.getInjectionConfig()).ifPresent(t -> {
                    t.beforeOutputFile(tableInfo, objectMap);
                    // 输出自定义文件
                    outputCustomFile(t.getCustomFile(), tableInfo, objectMap);
                });
                // entity
                outputEntity(tableInfo, objectMap);
                // mapper and xml
                outputMapper(tableInfo, objectMap);
                // service
                outputService(tableInfo, objectMap);
                // controller
                outputController(tableInfo, objectMap);
            });
        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    /**
     * 将模板转化成为文件，供子类重写
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     * @throws Exception 异常
     * @since 3.5.0
     */
    public void write(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception {

    }

    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
        if (StringUtils.isBlank(outDir) || !new File(outDir).exists()) {
            System.err.println("未找到输出目录：" + outDir);
        } else if (getConfigBuilder().getGlobalConfig().isOpen()) {
            try {
                RuntimeUtils.openDir(outDir);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * FreeMarker
     * 渲染对象 MAP 信息
     * @param config    配置信息
     * @param tableInfo 表信息对象
     * @return ignore
     */

    public Map<String, Object> getObjectMap(ConfigBuilder config, TableInfo tableInfo) {
        StrategyConfig strategyConfig = config.getStrategyConfig();
        Map<String, Object> controllerData = strategyConfig.controller().renderData(tableInfo);
        Map<String, Object> objectMap = new HashMap<>(controllerData);
        Map<String, Object> mapperData = strategyConfig.mapper().renderData(tableInfo);
        objectMap.putAll(mapperData);
        Map<String, Object> serviceData = strategyConfig.service().renderData(tableInfo);
        objectMap.putAll(serviceData);
        Map<String, Object> entityData = strategyConfig.entity().renderData(tableInfo);
        objectMap.putAll(entityData);
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageConfig().getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger", globalConfig.isSwagger());
        objectMap.put("date", globalConfig.getCommentDate());
        // 启用 schema 处理逻辑
        String schemaName = "";
        if (strategyConfig.isEnableSchema()) {
            // 存在 schemaName 设置拼接 . 组合表名
            schemaName = config.getDataSourceConfig().getSchemaName();
            if (StringUtils.isNotBlank(schemaName)) {
                schemaName += ".";
                tableInfo.setConvert(true);
            }
        }
        objectMap.put("schemaName", schemaName);
        objectMap.put("table", tableInfo);
        objectMap.put("entity", tableInfo.getEntityName());
        return objectMap;
    }

    /**
     * 模板真实文件路径
     * @param filePath 文件路径
     * @return ignore
     */
    public abstract String templateFilePath(String filePath);

    /**
     * 检测文件是否存在（3.5.3版本会删除此方法）
     * @return 文件是否存在
     * @deprecated 3.5.0
     */
    @Deprecated
    protected boolean isCreate(String filePath) {
        return isCreate(new File(filePath));
    }

    /**
     * 检查文件是否创建文件（3.5.3版本会删除此方法）
     * @param file 文件
     * @return 是否创建文件
     * @since 3.5.0
     */
    @Deprecated
    protected boolean isCreate(File file) {
        // 全局判断【默认】
        return !file.exists() || getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    /**
     * 检查文件是否创建文件
     * @param file         文件
     * @param fileOverride 是否覆盖已有文件
     * @return 是否创建文件
     * @since 3.5.2
     */
    protected boolean isCreate(File file, boolean fileOverride) {
        // 全局判断【默认】
        return !file.exists() || fileOverride;
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return getConfigBuilder().getGlobalConfig().isKotlin() ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
    }


    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }


    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }
}
