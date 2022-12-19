package io.devpl.toolkit.fxui.config;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CodeGenConfigurationModel {

    private final StringProperty name = new SimpleStringProperty(); // 本配置的名称
    private final StringProperty connectorJarPath = new SimpleStringProperty();

    /**
     * 项目所在根路径
     */
    private final StringProperty projectFolder = new SimpleStringProperty();

    /**
     * model类所在包的包名
     */
    private final StringProperty modelPackage = new SimpleStringProperty();

    /**
     * 父包名
     */
    private final StringProperty parentPackage = new SimpleStringProperty();

    /**
     * 是否使用mybatis
     */
    private final BooleanProperty useMyBatisPlus = new SimpleBooleanProperty(true);

    private final StringProperty modelPackageTargetFolder = new SimpleStringProperty();

    private final StringProperty daoPackage = new SimpleStringProperty();

    private final StringProperty daoTargetFolder = new SimpleStringProperty();

    private final StringProperty mapperName = new SimpleStringProperty();

    private final StringProperty mappingXMLPackage = new SimpleStringProperty();

    private final StringProperty mappingXMLTargetFolder = new SimpleStringProperty();

    private final StringProperty tableName = new SimpleStringProperty();

    private final StringProperty domainObjectName = new SimpleStringProperty();

    private final BooleanProperty offsetLimit = new SimpleBooleanProperty();

    private final BooleanProperty comment = new SimpleBooleanProperty();

    private final BooleanProperty overrideXML = new SimpleBooleanProperty();

    private final BooleanProperty needToStringHashcodeEquals = new SimpleBooleanProperty();

    private final BooleanProperty useLombokPlugin = new SimpleBooleanProperty();

    private final BooleanProperty needForUpdate = new SimpleBooleanProperty();

    private final BooleanProperty annotationDAO = new SimpleBooleanProperty();

    private final BooleanProperty annotation = new SimpleBooleanProperty();

    private final BooleanProperty useActualColumnNames = new SimpleBooleanProperty();

    private final BooleanProperty useExample = new SimpleBooleanProperty();

    private final BooleanProperty generateKeys = new SimpleBooleanProperty();

    private final StringProperty encoding = new SimpleStringProperty();

    private final BooleanProperty useTableNameAlias = new SimpleBooleanProperty();

    private final BooleanProperty useDAOExtendStyle = new SimpleBooleanProperty();

    private final BooleanProperty useSchemaPrefix = new SimpleBooleanProperty();

    private final BooleanProperty jsr310Support = new SimpleBooleanProperty();

    /**
     * 是否支持swagger
     */
    private final BooleanProperty swaggerSupport = new SimpleBooleanProperty();

    /**
     * 是否支持MVC
     */
    private final BooleanProperty fullMVCSupport = new SimpleBooleanProperty();

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getConnectorJarPath() {
        return connectorJarPath.get();
    }

    public StringProperty connectorJarPathProperty() {
        return connectorJarPath;
    }

    public void setConnectorJarPath(String connectorJarPath) {
        this.connectorJarPath.set(connectorJarPath);
    }

    public String getProjectFolder() {
        return projectFolder.get();
    }

    public StringProperty projectFolderProperty() {
        return projectFolder;
    }

    public void setProjectFolder(String projectFolder) {
        this.projectFolder.set(projectFolder);
    }

    public String getModelPackage() {
        return modelPackage.get();
    }

    public StringProperty modelPackageProperty() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage.set(modelPackage);
    }

    public String getParentPackage() {
        return parentPackage.get();
    }

    public StringProperty parentPackageProperty() {
        return parentPackage;
    }

    public void setParentPackage(String parentPackage) {
        this.parentPackage.set(parentPackage);
    }

    public boolean isUseMyBatisPlus() {
        return useMyBatisPlus.get();
    }

    public BooleanProperty useMyBatisPlusProperty() {
        return useMyBatisPlus;
    }

    public void setUseMyBatisPlus(boolean useMyBatisPlus) {
        this.useMyBatisPlus.set(useMyBatisPlus);
    }

    public String getModelPackageTargetFolder() {
        return modelPackageTargetFolder.get();
    }

    public StringProperty modelPackageTargetFolderProperty() {
        return modelPackageTargetFolder;
    }

    public void setModelPackageTargetFolder(String modelPackageTargetFolder) {
        this.modelPackageTargetFolder.set(modelPackageTargetFolder);
    }

    public String getDaoPackage() {
        return daoPackage.get();
    }

    public StringProperty daoPackageProperty() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage.set(daoPackage);
    }

    public String getDaoTargetFolder() {
        return daoTargetFolder.get();
    }

    public StringProperty daoTargetFolderProperty() {
        return daoTargetFolder;
    }

    public void setDaoTargetFolder(String daoTargetFolder) {
        this.daoTargetFolder.set(daoTargetFolder);
    }

    public String getMapperName() {
        return mapperName.get();
    }

    public StringProperty mapperNameProperty() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName.set(mapperName);
    }

    public String getMappingXMLPackage() {
        return mappingXMLPackage.get();
    }

    public StringProperty mappingXMLPackageProperty() {
        return mappingXMLPackage;
    }

    public void setMappingXMLPackage(String mappingXMLPackage) {
        this.mappingXMLPackage.set(mappingXMLPackage);
    }

    public String getMappingXMLTargetFolder() {
        return mappingXMLTargetFolder.get();
    }

    public StringProperty mappingXMLTargetFolderProperty() {
        return mappingXMLTargetFolder;
    }

    public void setMappingXMLTargetFolder(String mappingXMLTargetFolder) {
        this.mappingXMLTargetFolder.set(mappingXMLTargetFolder);
    }

    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    public String getDomainObjectName() {
        return domainObjectName.get();
    }

    public StringProperty domainObjectNameProperty() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName.set(domainObjectName);
    }

    public boolean isOffsetLimit() {
        return offsetLimit.get();
    }

    public BooleanProperty offsetLimitProperty() {
        return offsetLimit;
    }

    public void setOffsetLimit(boolean offsetLimit) {
        this.offsetLimit.set(offsetLimit);
    }

    public boolean isComment() {
        return comment.get();
    }

    public BooleanProperty commentProperty() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment.set(comment);
    }

    public boolean isOverrideXML() {
        return overrideXML.get();
    }

    public BooleanProperty overrideXMLProperty() {
        return overrideXML;
    }

    public void setOverrideXML(boolean overrideXML) {
        this.overrideXML.set(overrideXML);
    }

    public boolean isNeedToStringHashcodeEquals() {
        return needToStringHashcodeEquals.get();
    }

    public BooleanProperty needToStringHashcodeEqualsProperty() {
        return needToStringHashcodeEquals;
    }

    public void setNeedToStringHashcodeEquals(boolean needToStringHashcodeEquals) {
        this.needToStringHashcodeEquals.set(needToStringHashcodeEquals);
    }

    public boolean isUseLombokPlugin() {
        return useLombokPlugin.get();
    }

    public BooleanProperty useLombokPluginProperty() {
        return useLombokPlugin;
    }

    public void setUseLombokPlugin(boolean useLombokPlugin) {
        this.useLombokPlugin.set(useLombokPlugin);
    }

    public boolean isNeedForUpdate() {
        return needForUpdate.get();
    }

    public BooleanProperty needForUpdateProperty() {
        return needForUpdate;
    }

    public void setNeedForUpdate(boolean needForUpdate) {
        this.needForUpdate.set(needForUpdate);
    }

    public boolean isAnnotationDAO() {
        return annotationDAO.get();
    }

    public BooleanProperty annotationDAOProperty() {
        return annotationDAO;
    }

    public void setAnnotationDAO(boolean annotationDAO) {
        this.annotationDAO.set(annotationDAO);
    }

    public boolean isAnnotation() {
        return annotation.get();
    }

    public BooleanProperty annotationProperty() {
        return annotation;
    }

    public void setAnnotation(boolean annotation) {
        this.annotation.set(annotation);
    }

    public boolean isUseActualColumnNames() {
        return useActualColumnNames.get();
    }

    public BooleanProperty useActualColumnNamesProperty() {
        return useActualColumnNames;
    }

    public void setUseActualColumnNames(boolean useActualColumnNames) {
        this.useActualColumnNames.set(useActualColumnNames);
    }

    public boolean isUseExample() {
        return useExample.get();
    }

    public BooleanProperty useExampleProperty() {
        return useExample;
    }

    public void setUseExample(boolean useExample) {
        this.useExample.set(useExample);
    }

    public boolean isGenerateKeys() {
        return generateKeys.get();
    }

    public BooleanProperty generateKeysProperty() {
        return generateKeys;
    }

    public void setGenerateKeys(boolean generateKeys) {
        this.generateKeys.set(generateKeys);
    }

    public String getEncoding() {
        return encoding.get();
    }

    public StringProperty encodingProperty() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding.set(encoding);
    }

    public boolean isUseTableNameAlias() {
        return useTableNameAlias.get();
    }

    public BooleanProperty useTableNameAliasProperty() {
        return useTableNameAlias;
    }

    public void setUseTableNameAlias(boolean useTableNameAlias) {
        this.useTableNameAlias.set(useTableNameAlias);
    }

    public boolean isUseDAOExtendStyle() {
        return useDAOExtendStyle.get();
    }

    public BooleanProperty useDAOExtendStyleProperty() {
        return useDAOExtendStyle;
    }

    public void setUseDAOExtendStyle(boolean useDAOExtendStyle) {
        this.useDAOExtendStyle.set(useDAOExtendStyle);
    }

    public boolean isUseSchemaPrefix() {
        return useSchemaPrefix.get();
    }

    public BooleanProperty useSchemaPrefixProperty() {
        return useSchemaPrefix;
    }

    public void setUseSchemaPrefix(boolean useSchemaPrefix) {
        this.useSchemaPrefix.set(useSchemaPrefix);
    }

    public boolean isJsr310Support() {
        return jsr310Support.get();
    }

    public BooleanProperty jsr310SupportProperty() {
        return jsr310Support;
    }

    public void setJsr310Support(boolean jsr310Support) {
        this.jsr310Support.set(jsr310Support);
    }

    public boolean isSwaggerSupport() {
        return swaggerSupport.get();
    }

    public BooleanProperty swaggerSupportProperty() {
        return swaggerSupport;
    }

    public void setSwaggerSupport(boolean swaggerSupport) {
        this.swaggerSupport.set(swaggerSupport);
    }

    public boolean isFullMVCSupport() {
        return fullMVCSupport.get();
    }

    public BooleanProperty fullMVCSupportProperty() {
        return fullMVCSupport;
    }

    public void setFullMVCSupport(boolean fullMVCSupport) {
        this.fullMVCSupport.set(fullMVCSupport);
    }

    @Override
    public String toString() {
        return "CodeGenConfigurationModel{" +
                "name=" + name +
                ", connectorJarPath=" + connectorJarPath +
                ", projectFolder=" + projectFolder +
                ", modelPackage=" + modelPackage +
                ", parentPackage=" + parentPackage +
                ", useMyBatisPlus=" + useMyBatisPlus +
                ", modelPackageTargetFolder=" + modelPackageTargetFolder +
                ", daoPackage=" + daoPackage +
                ", daoTargetFolder=" + daoTargetFolder +
                ", mapperName=" + mapperName +
                ", mappingXMLPackage=" + mappingXMLPackage +
                ", mappingXMLTargetFolder=" + mappingXMLTargetFolder +
                ", tableName=" + tableName +
                ", domainObjectName=" + domainObjectName +
                ", offsetLimit=" + offsetLimit +
                ", comment=" + comment +
                ", overrideXML=" + overrideXML +
                ", needToStringHashcodeEquals=" + needToStringHashcodeEquals +
                ", useLombokPlugin=" + useLombokPlugin +
                ", needForUpdate=" + needForUpdate +
                ", annotationDAO=" + annotationDAO +
                ", annotation=" + annotation +
                ", useActualColumnNames=" + useActualColumnNames +
                ", useExample=" + useExample +
                ", generateKeys=" + generateKeys +
                ", encoding=" + encoding +
                ", useTableNameAlias=" + useTableNameAlias +
                ", useDAOExtendStyle=" + useDAOExtendStyle +
                ", useSchemaPrefix=" + useSchemaPrefix +
                ", jsr310Support=" + jsr310Support +
                ", swaggerSupport=" + swaggerSupport +
                ", fullMVCSupport=" + fullMVCSupport +
                '}';
    }
}
