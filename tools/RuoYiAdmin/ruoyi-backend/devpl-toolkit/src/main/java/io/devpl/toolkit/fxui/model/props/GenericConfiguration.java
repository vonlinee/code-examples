package io.devpl.toolkit.fxui.model.props;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 除了数据库配置外的配置:通用配置项
 */
public class GenericConfiguration {

    /**
     * 本配置的名称
     */
    private final StringProperty name = new SimpleStringProperty();

    private String connectorJarPath;

    private final StringProperty projectFolder = new SimpleStringProperty(); // 项目所在根路径
    private final StringProperty modelPackage = new SimpleStringProperty(); // model类所在包名
    private final StringProperty parentPackage = new SimpleStringProperty("org.example"); // 父包名
    private final BooleanProperty useMyBatisPlus = new SimpleBooleanProperty(true); // 是否使用mybatis
    private final StringProperty modelPackageTargetFolder = new SimpleStringProperty("src/main/java");
    private final StringProperty daoPackage = new SimpleStringProperty();
    private final StringProperty daoTargetFolder = new SimpleStringProperty("");
    private final StringProperty mapperName = new SimpleStringProperty("");
    private final StringProperty mappingXMLPackage = new SimpleStringProperty("");
    private final StringProperty mappingXMLTargetFolder = new SimpleStringProperty("");
    private final StringProperty tableName = new SimpleStringProperty();

    // 表映射的实体名称：表名一般是 table_name -> TableName
    private final StringProperty domainObjectName = new SimpleStringProperty();

    public String getConnectorJarPath() {
        return connectorJarPath;
    }

    public void setConnectorJarPath(String connectorJarPath) {
        this.connectorJarPath = connectorJarPath;
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

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
