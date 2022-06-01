package org.mybatis.generator.codegen.mybatis3;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedKotlinFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.AbstractKotlinGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Introspected table implementation for generating MyBatis3 artifacts.
 * Mybatis3的实现版本
 * @author Jeff Butler
 */
public class IntrospectedTableMyBatis3Impl extends IntrospectedTable {

	private static final Logger log = LoggerFactory.getLogger(IntrospectedTableMyBatis3Impl.class);

    protected final List<AbstractJavaGenerator> javaGenerators = new ArrayList<>();

    protected final List<AbstractKotlinGenerator> kotlinGenerators = new ArrayList<>();

    protected AbstractXmlGenerator xmlMapperGenerator;

    public IntrospectedTableMyBatis3Impl() {
        super(TargetRuntime.MYBATIS3);
    }

    @Override
    public void calculateGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
    	// Java实体类生成
        calculateJavaModelGenerators(warnings, progressCallback);
        // org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator@c730b35
        // Mapper类生成，根据不同的类型不同的实例
        AbstractJavaClientGenerator javaClientGenerator =
                calculateClientGenerators(warnings, progressCallback);
        // XML生成
        calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
    }

    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator,
            List<String> warnings,
            ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new XMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }

        initializeAbstractGenerator(xmlMapperGenerator, warnings,
                progressCallback);
    }

    protected AbstractJavaClientGenerator calculateClientGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (!rules.generateJavaClient()) {
            return null;
        }

        AbstractJavaClientGenerator javaGenerator = createJavaClientGenerator();
        if (javaGenerator == null) {
            return null;
        }
        // 初始化
        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        javaGenerators.add(javaGenerator);

        return javaGenerator;
    }

    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }
        // 客户端的类型
        String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();
        log.info("创建JavaClientGenerator, type:{}", type);
        AbstractJavaClientGenerator javaGenerator;
        if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$  基于XML和Mapper接口
            javaGenerator = new JavaMapperGenerator(getClientProject());
        } else if ("MIXEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new MixedClientGenerator(getClientProject());
        } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$  基于注解和Mapper接口
            javaGenerator = new AnnotatedClientGenerator(getClientProject());
        } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator(getClientProject());
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
        }
        return javaGenerator;
    }

    /**
     * Java对象生成的个数
     * @param warnings
     * @param progressCallback
     */
    protected void calculateJavaModelGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
    	// 生成EXAMPLE类
    	log.info("javaGenerators {}个", javaGenerators.size());
        if (getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator(getExampleProject());
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
        }

        if (getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator(getModelProject());
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
        }

        if (getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator(getModelProject());
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
        }

        if (getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator(getModelProject());
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
        }
        log.info("javaGenerators {}个", javaGenerators.size());
        log.info("{}", javaGenerators);
    }

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
        log.info("javaGenerators {}", javaGenerators);
        for (AbstractJavaGenerator javaGenerator : javaGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
            // TopLevelClass, Interface, Enum
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                                javaGenerator.getProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
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
        if (StringUtils.stringHasValue(project)) {
            return project;
        } else {
            return getModelProject();
        }
    }

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        log.info("获取生成的XML文件信息");
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
        AbstractJavaClientGenerator javaClientGenerator = createJavaClientGenerator();
        if (javaClientGenerator == null) {
            return false;
        } else {
            return javaClientGenerator.requiresXMLGenerator();
        }
    }
}
