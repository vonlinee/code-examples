package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.config.*;
import io.devpl.codegen.mbpg.config.po.IntrospectedTable;
import io.devpl.codegen.mbpg.config.po.TableField;
import io.devpl.codegen.mbpg.util.StringUtils;
import io.devpl.codegen.utils.FileUtils;
import io.devpl.codegen.utils.RuntimeUtils;
import io.devpl.codegen.utils.StringPool;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * 模板引擎抽象类
 * 屏蔽具体的模板引擎差异性
 */
public abstract class AbstractTemplateEngine implements ContextAware {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置信息
     */
    private Context context;

    /**
     * 模板引擎初始化
     */
    public abstract void init(Context context);

    /**
     * 输出自定义模板文件
     * @param customFiles 自定义模板文件列表
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @since 3.5.3
     */
    protected void outputCustomFile(List<CustomFile> customFiles, IntrospectedTable tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String parentPath = getPathInfo(OutputFile.PARENT);
        customFiles.forEach(file -> {
            String filePath = StringUtils.hasText(file.getFilePath()) ? file.getFilePath() : parentPath;
            if (StringUtils.hasText(file.getPackageName())) {
                filePath = filePath + File.separator + file.getPackageName();
                filePath = filePath.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
            }
            String fileName = filePath + File.separator + entityName + file.getFileName();
            outputFile(new File(fileName), objectMap, file.getTemplatePath(), file.isFileOverride());
        });
    }

    /**
     * 输出实体文件
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputEntity(IntrospectedTable tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = getPathInfo(OutputFile.ENTITY_KOTLIN);
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
            TemplateConfiguration templateConfig = context.getTemplateConfiguration();
            boolean useKotlin = context.getGlobalConfig().isKotlin();
            // 实体类模板文件路径
            String entityTemplate = templateConfig.getEntityTemplatePath(useKotlin);
            if (StringUtils.hasText(entityTemplate)) {
                // 扩展名
                String extension = useKotlin ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
                // 文件类型绝对路径
                String entityFile = entityPath + File.separator + entityName + extension;
                boolean fileOverride = context.getStrategyConfig().entity().isFileOverride();
                String entityTemplatePath = getTemplateFilePath(entityTemplate);
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
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputMapper(IntrospectedTable tableInfo, Map<String, Object> objectMap) {
        // MpMapper.java
        final TemplateConfiguration tc = context.getTemplateConfiguration();
        String entityName = tableInfo.getEntityName();
        String mapperPath = getPathInfo(OutputFile.MAPPER);
        if (StringUtils.hasText(tableInfo.getMapperName()) && StringUtils.isNotBlank(mapperPath)) {
            // Mapper 模板
            final String mapperTemplate = tc.getTemplate(OutputFile.MAPPER);
            String mapperFile = String.format((mapperPath + File.separator + tableInfo.getMapperName() + ".java"), entityName);
            outputFile(new File(mapperFile), objectMap, mapperTemplate, context.getStrategyConfig().mapper().isFileOverride());
        }
        // MpMapper.xml
        String xmlPath = getPathInfo(OutputFile.XML);
        if (StringUtils.isNotBlank(tableInfo.getXmlName()) && StringUtils.isNotBlank(xmlPath)) {
            final String xmlTemplate = tc.getTemplate(OutputFile.XML);
            String xmlFile = String.format((xmlPath + File.separator + tableInfo.getXmlName() + ".xml"), entityName);
            outputFile(new File(xmlFile), objectMap, xmlTemplate, context.getStrategyConfig().mapper().isFileOverride());
        }
    }

    /**
     * 输出service文件
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputService(IntrospectedTable tableInfo, Map<String, Object> objectMap) {
        // IMpService.java
        String entityName = tableInfo.getEntityName();
        final TemplateConfiguration tc = context.getTemplateConfiguration();
        String servicePath = getPathInfo(OutputFile.SERVICE);
        if (StringUtils.hasText(tableInfo.getServiceName()) && StringUtils.hasText(servicePath)) {
            final String serviceTemplate = tc.getTemplate(OutputFile.SERVICE);
            String serviceFile = String.format((servicePath + File.separator + tableInfo.getServiceName() + ".java"), entityName);
            outputFile(new File(serviceFile), objectMap, serviceTemplate, context.getStrategyConfig().service().isFileOverride());
        }
        // MpServiceImpl.java
        String serviceImplPath = getPathInfo(OutputFile.SERVICE_IMPL);
        if (StringUtils.hasText(tableInfo.getServiceImplName()) && StringUtils.hasText(serviceImplPath)) {
            final String serviceImplTemplate = tc.getTemplate(OutputFile.SERVICE_IMPL);
            String implFile = String.format((serviceImplPath + File.separator + tableInfo.getServiceImplName() + ".java"), entityName);
            outputFile(new File(implFile), objectMap, serviceImplTemplate, context.getStrategyConfig().service().isFileOverride());
        }
    }

    /**
     * 输出controller文件
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     */
    protected void outputController(IntrospectedTable tableInfo, Map<String, Object> objectMap) {
        // 绝对路径
        String controllerPath = getPathInfo(OutputFile.CONTROLLER);
        if (StringUtils.hasText(tableInfo.getControllerName()) && StringUtils.hasText(controllerPath)) {
            TemplateConfiguration tc = context.getTemplateConfiguration();
            String controllerTemplate = tc.getTemplate(OutputFile.CONTROLLER);
            String entityName = tableInfo.getEntityName();
            String controllerFile = String.format((controllerPath + File.separator + tableInfo.getControllerName() + ".java"), entityName);
            outputFile(new File(controllerFile), objectMap, controllerTemplate, context.getStrategyConfig().controller().isFileOverride());
        }
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
        if (isCreate(file, fileOverride)) {
            try {
                // 全局判断【默认】
                boolean exist = file.exists();
                if (!exist) {
                    File parentFile = file.getParentFile();
                    FileUtils.forceMkdir(parentFile);
                }
                try (FileWriter fw = new FileWriter(file)) {
                    write(objectMap, templatePath, fw);
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * 获取路径信息
     * @param outputFile 输出文件
     * @return 路径信息
     */
    @Nullable
    protected String getPathInfo(OutputFile outputFile) {
        return context.getPathInfo().get(outputFile);
    }

    /**
     * 将模板转化成为文件
     * @param objectMap 渲染对象 MAP 信息
     * @param template  模板，可能是字符串模板，可能是指向文件模板的路径，可能是指向模板文件的URL，有些模板引擎API不支持通过输入流获取模板
     * @param writer    文件生成输出位置
     */
    public abstract void write(Map<String, Object> objectMap, String template, Writer writer) throws Exception;

    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = context.getGlobalConfig().getOutputDir();
        if (!StringUtils.hasText(outDir) || !new File(outDir).exists()) {
            log.error("未找到输出目录 {}", outDir);
        } else if (context.getGlobalConfig().isOpenOutputDir()) {
            try {
                RuntimeUtils.openDirectory(outDir);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 模板真实文件路径
     * @param filePath 文件路径
     * @return ignore
     */
    public abstract String getTemplateFilePath(String filePath);

    /**
     * 检查文件是否创建文件
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

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
