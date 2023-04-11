package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.config.*;
import io.devpl.codegen.mbpg.config.builder.Context;
import io.devpl.codegen.mbpg.config.builder.CustomFile;
import io.devpl.codegen.mbpg.config.po.TableField;
import io.devpl.codegen.mbpg.config.po.TableInfo;
import io.devpl.codegen.mbpg.util.FileUtils;
import io.devpl.codegen.mbpg.util.RuntimeUtils;
import io.devpl.codegen.mbpg.util.StringPool;
import io.devpl.codegen.mbpg.util.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 模板引擎抽象类
 * 屏蔽具体的模板引擎差异性
 */
public abstract class AbstractTemplateEngine {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置信息
     */
    private Context context;

    /**
     * 模板引擎初始化
     */
    public abstract AbstractTemplateEngine init(Context context);

    /**
     * 输出自定义模板文件
     *
     * @param customFiles 自定义模板文件列表
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @since 3.5.3
     */
    protected void outputCustomFile(List<CustomFile> customFiles, TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String parentPath = getPathInfo(OutputFile.PARENT);
        customFiles.forEach(file -> {
            String filePath = io.devpl.sdk.util.StringUtils.hasText(file.getFilePath()) ? file.getFilePath() : parentPath;
            if (io.devpl.sdk.util.StringUtils.hasText(file.getPackageName())) {
                filePath = filePath + File.separator + file.getPackageName();
                filePath = filePath.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
            }
            String fileName = filePath + File.separator + entityName + file.getFileName();
            outputFile(new File(fileName), objectMap, file.getTemplatePath(), file.isFileOverride());
        });
    }

    /**
     * 输出实体文件
     *
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputEntity(TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = getPathInfo(OutputFile.ENTITY);
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
            Context config = getContext();
            TemplateConfiguration templateConfig = config.getTemplateConfiguration();
            boolean useKotlin = config.getGlobalConfig().isKotlin();
            // 实体类模板文件路径
            String entity = templateConfig.getEntityTemplatePath(useKotlin);
            if (StringUtils.hasLength(entity)) {
                // 扩展名
                String extension = useKotlin ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
                // 文件类型绝对路径
                String entityFile = entityPath + File.separator + entityName + extension;
                boolean fileOverride = config.getStrategyConfig().entity().isFileOverride();

                String entityTemplatePath = templateFilePath(entity);
                for (TableField field : tableInfo.getFields()) {
                    if (!StringUtils.hasLength(field.getComment())) {
                        field.setComment(" "); // 占位
                    }
                }
                outputFile(new File(entityFile), objectMap, entityTemplatePath, fileOverride);
            }
        }
    }

    /**
     * 输出Mapper文件(含xml)
     *
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputMapper(TableInfo tableInfo, Map<String, Object> objectMap) {
        // MpMapper.java
        final Context config = getContext();
        String entityName = tableInfo.getEntityName();
        String mapperPath = getPathInfo(OutputFile.MAPPER);
        if (StringUtils.hasText(tableInfo.getMapperName()) && StringUtils.isNotBlank(mapperPath)) {
            getTemplateFilePath(TemplateConfiguration::getMapperTemplatePath).ifPresent(mapper -> {
                String mapperFile = String.format((mapperPath + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                outputFile(new File(mapperFile), objectMap, mapper, config.getStrategyConfig().mapper().isFileOverride());
            });
        }
        // MpMapper.xml
        String xmlPath = getPathInfo(OutputFile.XML);
        if (StringUtils.isNotBlank(tableInfo.getXmlName()) && StringUtils.isNotBlank(xmlPath)) {
            getTemplateFilePath(TemplateConfiguration::getXml).ifPresent(xml -> {
                String xmlFile = String.format((xmlPath + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                outputFile(new File(xmlFile), objectMap, xml, config.getStrategyConfig().mapper().isFileOverride());
            });
        }
    }

    /**
     * 输出service文件
     *
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputService(TableInfo tableInfo, Map<String, Object> objectMap) {
        // IMpService.java
        final Context config = getContext();
        String entityName = tableInfo.getEntityName();
        String servicePath = getPathInfo(OutputFile.SERVICE);
        if (io.devpl.sdk.util.StringUtils.isNotBlank(tableInfo.getServiceName()) && io.devpl.sdk.util.StringUtils.isNotBlank(servicePath)) {
            getTemplateFilePath(TemplateConfiguration::getService).ifPresent(service -> {
                String serviceFile = String.format((servicePath + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                outputFile(new File(serviceFile), objectMap, service, config.getStrategyConfig().service().isFileOverride());
            });
        }
        // MpServiceImpl.java
        String serviceImplPath = getPathInfo(OutputFile.SERVICE_IMPL);
        if (io.devpl.sdk.util.StringUtils.isNotBlank(tableInfo.getServiceImplName()) && io.devpl.sdk.util.StringUtils.isNotBlank(serviceImplPath)) {
            getTemplateFilePath(TemplateConfiguration::getServiceImpl).ifPresent(serviceImpl -> {
                String implFile = String.format((serviceImplPath + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                outputFile(new File(implFile), objectMap, serviceImpl, config.getStrategyConfig().service().isFileOverride());
            });
        }
    }

    /**
     * 输出controller文件
     *
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputController(TableInfo tableInfo, Map<String, Object> objectMap) {
        // MpController.java
        String controllerPath = getPathInfo(OutputFile.CONTROLLER);
        if (io.devpl.sdk.util.StringUtils.isNotBlank(tableInfo.getControllerName()) && io.devpl.sdk.util.StringUtils.isNotBlank(controllerPath)) {
            getTemplateFilePath(TemplateConfiguration::getController).ifPresent(controller -> {
                String entityName = tableInfo.getEntityName();
                String controllerFile = String.format((controllerPath + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                outputFile(new File(controllerFile), objectMap, controller, getContext().getStrategyConfig().controller().isFileOverride());
            });
        }
    }

    /**
     * 输出文件
     *
     * @param file         文件
     * @param objectMap    渲染信息
     * @param templatePath 模板路径
     * @param fileOverride 是否覆盖已有文件
     * @since 3.5.2
     */
    protected void outputFile(File file, Map<String, Object> objectMap, String templatePath, boolean fileOverride) {
        if (isCreate(file, fileOverride)) {
            try {
                // 全局判断【默认】
                boolean exist = file.exists();
                if (!exist) {
                    File parentFile = file.getParentFile();
                    FileUtils.forceMkdir(parentFile);
                }
                write(objectMap, templatePath, file);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * 获取模板路径
     *
     * @param function function
     * @return 模板路径
     * @since 3.5.0
     */

    protected Optional<String> getTemplateFilePath(Function<TemplateConfiguration, String> function) {
        TemplateConfiguration templateConfig = getContext().getTemplateConfiguration();
        String filePath = function.apply(templateConfig);
        if (io.devpl.sdk.util.StringUtils.isNotBlank(filePath)) {
            return Optional.of(templateFilePath(filePath));
        }
        return Optional.empty();
    }

    /**
     * 获取路径信息
     *
     * @param outputFile 输出文件
     * @return 路径信息
     */
    @Nullable
    protected String getPathInfo(OutputFile outputFile) {
        return getContext().getPathInfo().get(outputFile);
    }

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     * @throws Exception 异常
     * @since 3.5.0
     */
//    public void writer(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception {
//        this.writer(objectMap, templatePath, outputFile.getPath());
//        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
//    }
    public abstract void write(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception;

    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = context.getGlobalConfig().getOutputDir();
        if (StringUtils.isBlank(outDir) || !new File(outDir).exists()) {
            log.error("未找到输出目录 {}", outDir);
        } else if (getContext().getGlobalConfig().isOpenOutputDir()) {
            try {
                RuntimeUtils.openDirectory(outDir);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 模板真实文件路径
     *
     * @param filePath 文件路径
     * @return ignore
     */

    public abstract String templateFilePath(String filePath);

    /**
     * 检查文件是否创建文件
     *
     * @param file         文件
     * @param fileOverride 是否覆盖已有文件
     * @return 是否创建文件
     * @since 3.5.2
     */
    protected boolean isCreate(File file, boolean fileOverride) {
        if (file.exists() && !fileOverride) {
            log.warn("文件[{}]已存在，且未开启文件覆盖配置，需要开启配置可到策略配置中设置！！！", file.getAbsolutePath());
            return false;
        }
        return true;
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return getContext().getGlobalConfig().isKotlin() ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
    }

    public Context getContext() {
        return context;
    }

    public AbstractTemplateEngine setContext(Context context) {
        this.context = context;
        return this;
    }
}
