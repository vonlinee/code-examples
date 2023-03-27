package io.devpl.toolkit.mbp;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;
import io.devpl.toolkit.codegen.DefaultNameConverter;
import io.devpl.toolkit.codegen.JDBCDriver;
import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.dto.Constant;
import io.devpl.toolkit.dto.GenSetting;
import io.devpl.toolkit.dto.OutputFileInfo;
import io.devpl.toolkit.dto.UserConfig;
import io.devpl.toolkit.entity.JdbcConnInfo;
import io.devpl.toolkit.service.CodeGenConfigService;
import io.devpl.toolkit.service.ConnectionConfigService;
import io.devpl.toolkit.strategy.*;
import io.devpl.toolkit.utils.CollectionUtils;
import io.devpl.toolkit.utils.FileUtils;
import io.devpl.toolkit.utils.ProjectPathResolver;
import io.devpl.toolkit.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * MyBatis-Plus Generator 3.5.3.1
 * <a href="https://baomidou.com/pages/981406/">...</a>
 */
@Slf4j
@Component
public class MBPGBridge {

    @Resource
    private CodeGenConfigService userConfigStore;

    @Resource
    private ConnectionConfigService connConfigService;

    NameConverter nameConverter = new DefaultNameConverter();

    private final ProjectPathResolver projectPathResolver = new ProjectPathResolver("");

    /**
     * 根据所选择的配置生成CRUD代码
     */
    public void doGenerate(GenSetting genSetting, List<String> tables) {
        projectPathResolver.refreshBaseProjectPath(genSetting.getRootPath());
        BeetlTemplateEngine beetlTemplateEngine = new BeetlTemplateEngine(nameConverter, userConfigStore.getTemplateStoreDir());
        JdbcConnInfo connInfo = connConfigService.getOne(new LambdaQueryWrapper<JdbcConnInfo>().eq(JdbcConnInfo::getName, genSetting.getConnectionName()));
        if (connInfo == null) {
            throw new RuntimeException("连接不存在");
        }
        JDBCDriver driver = JDBCDriver.valueOf(connInfo.getDbType().toUpperCase());

        String dbName = connInfo.getDbName();
        if (!StringUtils.hasText(dbName)) {
            dbName = genSetting.getDatabaseName();
        }
        dbName = Objects.requireNonNull(dbName, "数据库名称为空");
        String connectionUrl = driver.getConnectionUrl(connInfo.getHost(), connInfo.getPort(), dbName, null);
        DataSourceConfig.Builder dsBuilder = new DataSourceConfig.Builder(connectionUrl, connInfo.getUsername(), connInfo.getPassword()).schema(connInfo.getDbName());

        log.info("connectionUrl {}", connectionUrl);

        // 生成策略配置
        UserConfig userConfig = userConfigStore.getDefaultUserConfig();
        FastAutoGenerator.create(dsBuilder).dataSourceConfig(builder -> {
            builder.schema(connInfo.getDbName());
            // builder.typeConvert(generatorConfig.getTypeConvert());
        }).globalConfig(builder -> {
            builder.dateType(DateType.ONLY_DATE); // TODO 配置化
            // 指定所有生成文件的根目录
            String sourcePath = projectPathResolver.getSourcePath();
            if (FileUtils.createDirectoriesQuitely(sourcePath)) {
                builder.outputDir(sourcePath);
            }
            builder.author(genSetting.getAuthor());
            if (userConfig.getEntityStrategy().isSwagger2()) {
                builder.enableSwagger();
            }
        }).templateEngine(beetlTemplateEngine).packageConfig(builder -> {
            configPackage(builder, genSetting, userConfig.getOutputFiles());
        }).templateConfig(builder -> {
            // 模板配置
            configTemplate(builder, genSetting.getChoosedOutputFiles(), userConfig);
        }).injectionConfig(builder -> {
            configInjection(builder, userConfig, genSetting);
        }).strategyConfig(builder -> {
            builder.addInclude(String.join(",", tables)).disableSqlFilter().enableSkipView();
            // 策略配置
            configEntity(builder.entityBuilder(), userConfig.getEntityStrategy(), genSetting.isOverride());
            configMapper(builder.mapperBuilder(), userConfig.getMapperStrategy(), userConfig.getMapperXmlStrategy(), genSetting.isOverride());
            configService(builder.serviceBuilder(), userConfig.getServiceStrategy(), userConfig.getServiceImplStrategy());
            configController(builder.controllerBuilder(), userConfig.getControllerStrategy());
        }).execute();
    }

    /**
     * 包配置
     *
     * @param builder         包配置
     * @param genSetting      生成设置信息
     * @param outputFileInfos 文件列表
     */
    private void configPackage(PackageConfig.Builder builder, GenSetting genSetting, List<OutputFileInfo> outputFileInfos) {
        OutputFileInfo fileInfo = null;
        for (OutputFileInfo outputFileInfo : outputFileInfos) {
            if ("Mapper.xml".equals(outputFileInfo.getFileType())) {
                fileInfo = outputFileInfo;
                break;
            }
        }
        if (fileInfo == null) {
            throw new BusinessException("文件不存在");
        }
        String mapperXmlOutputPath = projectPathResolver.convertPackageToPath(fileInfo.getOutputLocation());
        String moduleName = genSetting.getModuleName();

        String parentPackageName = null;
        if (!StringUtils.hasText(moduleName)) {
            mapperXmlOutputPath = mapperXmlOutputPath + File.separator + moduleName;
        } else {
            parentPackageName = genSetting.getBasePackageName() + "." + moduleName;
        }
        // 这里的模块名处理方式和原版的MPG不同，是将模块名放在包名最后
        String entityPkg = parentPackageName + ".entity";
        String mapperPkg = parentPackageName + ".mapper";
        String servicePkg = parentPackageName + ".service";
        String serviceImplPkg = parentPackageName + ".impl";
        String controllerPkg = parentPackageName + ".controller";

        Map<OutputFile, String> pathInfoMap = new HashMap<>();

        pathInfoMap.put(OutputFile.xml, mapperXmlOutputPath);

        // 子包名已经包含了完整路径
        builder.parent("")
                .moduleName("")
                .entity(entityPkg)
                .controller(controllerPkg)
                .mapper(mapperPkg)
                .service(servicePkg)
                .serviceImpl(serviceImplPkg)
                .pathInfo(pathInfoMap);
    }

    /**
     * 配置模板
     *
     * @param builder          模板配置
     * @param choosedFileTypes 选择生成的文件类型
     * @param userConfig       用户配置
     */
    private void configTemplate(TemplateConfig.Builder builder, List<String> choosedFileTypes, UserConfig userConfig) {

        Map<String, OutputFileInfo> fileInfoMap = CollectionUtils.toMap(userConfig.getOutputFiles(), OutputFileInfo::getFileType);

        OutputFileInfo entityFileInfo = fileInfoMap.get(Constant.FILE_TYPE_ENTITY);
        OutputFileInfo mapperFileInfo = fileInfoMap.get(Constant.FILE_TYPE_MAPPER);
        OutputFileInfo xmlFileInfo = fileInfoMap.get(Constant.FILE_TYPE_MAPPER_XML);
        OutputFileInfo serviceFileInfo = fileInfoMap.get(Constant.FILE_TYPE_SERVICE);
        OutputFileInfo serviceImplFileInfo = fileInfoMap.get(Constant.FILE_TYPE_SERVICEIMPL);
        OutputFileInfo controllerFileInfo = fileInfoMap.get(Constant.FILE_TYPE_CONTROLLER);

        Optional.ofNullable(entityFileInfo).ifPresent(item -> builder.entity(item.getAvailableTemplatePath()));
        Optional.ofNullable(mapperFileInfo).ifPresent(item -> builder.mapper(item.getAvailableTemplatePath()));
        Optional.ofNullable(xmlFileInfo).ifPresent(item -> builder.xml(item.getAvailableTemplatePath()));
        Optional.ofNullable(serviceFileInfo).ifPresent(item -> builder.service(item.getAvailableTemplatePath()));
        Optional.ofNullable(serviceImplFileInfo)
                .ifPresent(item -> builder.serviceImpl(item.getAvailableTemplatePath()));
        Optional.ofNullable(controllerFileInfo).ifPresent(item -> builder.controller(item.getAvailableTemplatePath()));

        // 禁用模板类型
        if (!choosedFileTypes.contains(Constant.FILE_TYPE_ENTITY)) {
            builder.disable(TemplateType.ENTITY);
        }
        if (!choosedFileTypes.contains(Constant.FILE_TYPE_MAPPER)) {
            builder.disable(TemplateType.MAPPER);
        }
        if (!choosedFileTypes.contains(Constant.FILE_TYPE_MAPPER_XML)) {
            builder.disable(TemplateType.XML);
        }
        if (!choosedFileTypes.contains(Constant.FILE_TYPE_SERVICE)) {
            builder.disable(TemplateType.SERVICE);
        }
        if (!choosedFileTypes.contains(Constant.FILE_TYPE_SERVICEIMPL)) {
            builder.disable(TemplateType.SERVICE_IMPL);
        }
        if (!choosedFileTypes.contains(Constant.FILE_TYPE_CONTROLLER)) {
            builder.disable(TemplateType.CONTROLLER);
        }
    }

    // 自定义模板参数配置
    private void configInjection(InjectionConfig.Builder builder, UserConfig userConfig, GenSetting genSetting) {
        // 自定义参数
        builder.beforeOutputFile((tableInfo, objectMap) -> {
            Map<String, Object> vars;
            TemplateVaribleInjecter varibleInjecter = tableInfo1 -> new HashMap<>();
            vars = varibleInjecter.getCustomTemplateVaribles(tableInfo);
            // 用于控制controller中对应API是否展示的自定义参数
            Map<String, Object> controllerMethodsVar = new HashMap<>();
            for (String method : genSetting.getChoosedControllerMethods()) {
                controllerMethodsVar.put(method, true);
            }
            if (controllerMethodsVar.size() > 0) {
                controllerMethodsVar.put("hasMethod", true);
            }
            vars.put("controllerMethods", controllerMethodsVar);
            if (!StringUtils.hasText(genSetting.getDatabaseName())) {
                vars.put("schemaName", genSetting.getDatabaseName() + ".");
            }
            objectMap.putAll(vars);
            log.info("EntityPath: {}", tableInfo.getEntityPath());
        });
        // 自定义文件生成
        for (OutputFileInfo outputFileInfo : userConfig.getOutputFiles()) {
            if (!outputFileInfo.isBuiltIn() && genSetting.getChoosedOutputFiles()
                    .contains(outputFileInfo.getFileType())) {
                CustomFile.Builder fileBuilder = new CustomFile.Builder();
                // 注意这里传入的是fileType,配合自定义的TemplateEngine.outputCustomFile生成自定义文件
                fileBuilder.fileName(outputFileInfo.getFileType());
                fileBuilder.templatePath(outputFileInfo.getTemplatePath());
                fileBuilder.packageName(outputFileInfo.getOutputPackage());
                if (genSetting.isOverride()) {
                    fileBuilder.enableFileOverride();
                }
                builder.customFile(fileBuilder.build());
            }
        }
    }

    /**
     * 检查代码生成设置
     *
     * @param genSetting 代码生成设置
     */
    public void checkGenSetting(GenSetting genSetting) {
        String rootPath = genSetting.getRootPath();
        if (!StringUtils.hasText(rootPath)) {
            throw new BusinessException("目标项目根目录不能为空");
        }
        genSetting.setRootPath(StringUtils.utf8Decode(rootPath));
        if (!new File(rootPath).isDirectory()) {
            throw new BusinessException("目标项目根目录错误，请确认目录有效且存在：" + rootPath);
        }
        if (!rootPath.endsWith(File.separator)) {
            genSetting.setRootPath(rootPath + File.separator);
        }
    }

    /**
     * 配置entity的生成信息
     */
    private void configEntity(Entity.Builder entityBuilder, EntityStrategy entityStrategy, boolean fileOverride) {
        // 主键ID类型
        entityBuilder.idType(IdType.ASSIGN_ID);
        entityBuilder.convertFileName(entityName -> entityName);
        entityBuilder.nameConvert(new INameConvert() {
            @Override
            @Nonnull
            public String entityNameConvert(@Nonnull TableInfo tableInfo) {
                return nameConverter.entityNameConvert(tableInfo.getName());
            }

            @Override
            @Nonnull
            public String propertyNameConvert(@Nonnull TableField field) {
                return nameConverter.propertyNameConvert(field.getName());
            }
        });
        entityBuilder.superClass(entityStrategy.getSuperEntityClass());
        if (fileOverride) {
            entityBuilder.enableFileOverride();
        }
        if (!entityStrategy.isEntitySerialVersionUID()) {
            entityBuilder.disableSerialVersionUID();
        }
        if (entityStrategy.isEntityBuilderModel()) {
            entityBuilder.enableChainModel();
        }
        if (entityStrategy.isEntityLombokModel()) {
            entityBuilder.enableLombok();
        }
        if (entityStrategy.isEntityBooleanColumnRemoveIsPrefix()) {
            entityBuilder.enableRemoveIsPrefix();
        }
        if (entityStrategy.isEntityTableFieldAnnotationEnable()) {
            entityBuilder.enableTableFieldAnnotation();
        }
        if (entityStrategy.isActiveRecord()) {
            entityBuilder.enableActiveRecord();
        }
        if (!StringUtils.hasText(entityStrategy.getVersionFieldName())) {
            entityBuilder.versionColumnName(entityStrategy.getVersionFieldName());
            entityBuilder.versionPropertyName(entityStrategy.getVersionFieldName());
        }
        if (!StringUtils.hasText(entityStrategy.getLogicDeleteFieldName())) {
            entityBuilder.logicDeleteColumnName(entityStrategy.getLogicDeleteFieldName());
            entityBuilder.logicDeletePropertyName(entityStrategy.getLogicDeleteFieldName());
        }
        if (entityStrategy.getSuperEntityColumns() != null) {
            entityBuilder.addSuperEntityColumns(entityStrategy.getSuperEntityColumns());
        }
        if (entityStrategy.getTableFills() != null && !entityStrategy.getTableFills().isEmpty()) {
            List<IFill> tableFills = new ArrayList<>();
            for (String tableFillStr : entityStrategy.getTableFills()) {
                if (!StringUtils.hasText(tableFillStr)) {
                    String[] tmp = tableFillStr.split(":");
                    IFill tableFill = new Column(tmp[0], FieldFill.valueOf(tmp[1].toUpperCase()));
                    tableFills.add(tableFill);
                }
            }
            entityBuilder.addTableFills(tableFills);
        }
    }

    /**
     * 配置mapper和mapper-xml
     */
    private void configMapper(Mapper.Builder mapperBuilder, MapperStrategy mapperStrategy, MapperXmlStrategy mapperXmlStrategy, boolean fileOverride) {
        if (mapperStrategy.getSuperMapperClass() != null) {
            mapperBuilder.superClass(mapperStrategy.getSuperMapperClass());
        }
        if (mapperXmlStrategy.isBaseResultMap()) {
            mapperBuilder.enableBaseResultMap();
            // TODO:enableBaseColumnList，cache目前没有页面配置
            mapperBuilder.enableBaseColumnList();
        }
        mapperBuilder.convertMapperFileName(nameConverter::mapperNameConvert);
        mapperBuilder.convertXmlFileName(nameConverter::mapperXmlNameConvert);
        if (fileOverride) {
            mapperBuilder.enableFileOverride();
        }
    }

    /**
     * 配置service
     */
    private void configService(Service.Builder serviceBuilder, ServiceStrategy serviceStrategy, ServiceImplStrategy serviceImplStrategy) {
        if (serviceStrategy.getSuperServiceClass() != null) {
            serviceBuilder.superServiceClass(serviceStrategy.getSuperServiceClass());
        }
        if (serviceImplStrategy.getSuperServiceImplClass() != null) {
            serviceBuilder.superServiceImplClass(serviceImplStrategy.getSuperServiceImplClass());
        }
        serviceBuilder.convertServiceFileName(nameConverter::serviceNameConvert);
        serviceBuilder.convertServiceImplFileName(nameConverter::serviceImplNameConvert);
    }

    /**
     * 配置Controller
     */
    private void configController(Controller.Builder controllerBuilder, ControllerStrategy controllerStrategy) {
        if (controllerStrategy.isRestControllerStyle()) {
            controllerBuilder.enableRestStyle();
        }
        if (controllerStrategy.isControllerMappingHyphenStyle()) {
            controllerBuilder.enableHyphenStyle();
        }
        if (controllerStrategy.getSuperControllerClass() != null) {
            controllerBuilder.superClass(controllerStrategy.getSuperControllerClass());
        }
        controllerBuilder.convertFileName(nameConverter::controllerNameConvert);
    }
}
