package org.mybatis.generator.codegen.mybatis3;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.*;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.springmvc.BusinessServiceGenerator;
import org.mybatis.generator.codegen.springmvc.ControllerGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Introspected table implementation for generating MyBatis3 artifacts.
 * 生成MVC全部文件，比如Controller等
 */
public class IntrospectedTableFullMVCImpl extends IntrospectedTable {

    private static final Logger log = LoggerFactory.getLogger(IntrospectedTableFullMVCImpl.class);

    protected final List<AbstractJavaGenerator> javaGenerators = new ArrayList<>();

    protected final List<AbstractKotlinGenerator> kotlinGenerators = new ArrayList<>();

    protected AbstractXmlGenerator xmlMapperGenerator;

    public IntrospectedTableFullMVCImpl() {
        super(TargetRuntime.MYBATIS3);
    }

    @Override
    public void calculateGenerators(List<String> warnings,
                                    ProgressCallback progressCallback) {
        // Java实体类生成器
        calculateJavaModelGenerators(warnings, progressCallback);
        // org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator@c730b35
        // Mapper类生成，根据不同的类型不同的实例
        AbstractMapperGenerator javaClientGenerator =
                calculateClientGenerators(warnings, progressCallback);

        log.info("[添加Mapper生成器] => {}", javaClientGenerator);
        // XML生成
        calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
    }

    protected void calculateXmlMapperGenerator(AbstractMapperGenerator javaClientGenerator,
                                               List<String> warnings,
                                               ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new XMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }
        log.info("[添加XML生成器] => {}", xmlMapperGenerator);
        initializeAbstractGenerator(xmlMapperGenerator, warnings,
                progressCallback);
    }

    protected AbstractMapperGenerator calculateClientGenerators(List<String> warnings,
                                                                ProgressCallback progressCallback) {
        if (!rules.generateJavaClient()) {
            return null;
        }

        AbstractMapperGenerator javaGenerator = createJavaClientGenerator();
        if (javaGenerator == null) {
            return null;
        }
        // 初始化
        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        javaGenerators.add(javaGenerator);

        return javaGenerator;
    }

    protected AbstractMapperGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }
        // 客户端的类型
        String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

        AbstractMapperGenerator javaGenerator;
        if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$  基于XML和Mapper接口
            javaGenerator = new JavaMapperGenerator(getClientProject());
        } else if ("MIXEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new MixedMapperGenerator(getClientProject());
        } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$  基于注解和Mapper接口
            javaGenerator = new AnnotatedMapperGenerator(getClientProject());
        } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator(getClientProject());
        } else {
            javaGenerator = (AbstractMapperGenerator) ObjectFactory.createInternalObject(type);
        }
        log.info("[创建Mapper生成器] => {} {}", type, javaGenerator);
        return javaGenerator;
    }

    /**
     * Java对象生成的个数
     *
     * @param warnings
     * @param progressCallback
     */
    protected void calculateJavaModelGenerators(List<String> warnings,
                                                ProgressCallback progressCallback) {
        // 生成EXAMPLE类
        if (getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator(getExampleProject());
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
            log.info("[添加Java生成器] => {}", javaGenerator);
        }

        if (getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator(getModelProject());
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
            log.info("[添加Java生成器] => {}", javaGenerator);
        }

        if (getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator(getModelProject());
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
            log.info("[添加Java生成器] => {}", javaGenerator);
        }

        if (getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator(getModelProject());
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
            log.info("[添加Java生成器] => {}", javaGenerator);
        }

        // 生成Controller和Service层代码
        // 新添加的功能
        AbstractJavaGenerator mvcControllerGenerator = new ControllerGenerator(getModelProject());
        initializeAbstractGenerator(mvcControllerGenerator, warnings, progressCallback);
        javaGenerators.add(mvcControllerGenerator);
        log.info("[添加Java生成器] => {}", mvcControllerGenerator);

        AbstractJavaGenerator mvcServiceGenerator = new BusinessServiceGenerator(getModelProject());
        initializeAbstractGenerator(mvcServiceGenerator, warnings, progressCallback);
        javaGenerators.add(mvcServiceGenerator);
        log.info("[添加Java生成器] => {}", mvcServiceGenerator);
    }

    /**
     * 初始化Generator，注入上下文
     *
     * @param abstractGenerator
     * @param warnings
     * @param progressCallback
     */
    protected void initializeAbstractGenerator(AbstractGenerator abstractGenerator,
                                               List<String> warnings, ProgressCallback progressCallback) {
        if (abstractGenerator == null) {
            return;
        }
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(this);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

    @Override
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        List<GeneratedJavaFile> answer = new ArrayList<>();

        for (AbstractJavaGenerator javaGenerator : javaGenerators) {
            log.info("[获取待生成的编译单元] => {}", javaGenerator);
            // 确定生成的文件的内容
            List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
            // TopLevelClass, Interface, Enum
            // 针对编译单元构造文件信息，然后进行生成
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        javaGenerator.getProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter());
                log.info("[生成文件信息] => {}", gjf.getTargetProject() + "/" + gjf.getFileName());
                answer.add(gjf);
            }
        }
        return answer;
    }

    @Override
    public List<GeneratedKotlinFile> getGeneratedKotlinFiles() {
        List<GeneratedKotlinFile> answer = new ArrayList<>();

        for (AbstractKotlinGenerator kotlinGenerator : kotlinGenerators) {
            List<KotlinFile> kotlinFiles = kotlinGenerator.getKotlinFiles();
            for (KotlinFile kotlinFile : kotlinFiles) {
                GeneratedKotlinFile gjf = new GeneratedKotlinFile(kotlinFile,
                        kotlinGenerator.getProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_KOTLIN_FILE_ENCODING),
                        context.getKotlinFormatter());
                answer.add(gjf);
            }
        }

        return answer;
    }

    protected String getClientProject() {
        return context.getJavaClientGeneratorConfiguration().getTargetProject();
    }

    protected String getModelProject() {
        return context.getJavaModelGeneratorConfiguration().getTargetProject();
    }

    protected String getExampleProject() {
        String project = context.getJavaModelGeneratorConfiguration().getProperty(
                PropertyRegistry.MODEL_GENERATOR_EXAMPLE_PROJECT);
        if (StringUtils.isNotEmpty(project)) {
            return project;
        } else {
            return getModelProject();
        }
    }

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<>();
        if (xmlMapperGenerator != null) {
            Document document = xmlMapperGenerator.getDocument();
            GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                    getMyBatis3XmlMapperFileName(), getMyBatis3XmlMapperPackage(),
                    context.getSqlMapGeneratorConfiguration().getTargetProject(),
                    true, context.getXmlFormatter());
            if (context.getPlugins().sqlMapGenerated(gxf, this)) {
                answer.add(gxf);
            }
        }

        return answer;
    }

    @Override
    public int getGenerationSteps() {
        return javaGenerators.size() + (xmlMapperGenerator == null ? 0 : 1);
    }

    @Override
    public boolean requiresXMLGenerator() {
        AbstractMapperGenerator javaClientGenerator = createJavaClientGenerator();
        if (javaClientGenerator == null) {
            return false;
        } else {
            return javaClientGenerator.requiresXMLGenerator();
        }
    }
}
