package io.devpl.toolkit.fxui.model.props;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.nio.file.Path;

/**
 * 除了数据库配置外的配置:通用配置项
 */
public class GenericConfiguration {

    /**
     * 本配置的名称
     */
    private final StringProperty name = new SimpleStringProperty();

    private String connectorJarPath;

    /**
     * 项目所在根路径
     */
    private final StringProperty projectFolder = new SimpleStringProperty();

    /**
     * 父包名
     */
    private final StringProperty parentPackage = new SimpleStringProperty("org.example");

    /**
     * 实体类所在包名
     */
    private final StringProperty modelPackage = new SimpleStringProperty(); //

    /**
     * 实体类存放目录
     */
    private final StringProperty modelPackageTargetFolder = new SimpleStringProperty("src/main/java");

    /**
     * Mapper接口包名
     */
    private final StringProperty daoPackage = new SimpleStringProperty();

    /**
     * Mapper接口存放目录
     */
    private final StringProperty daoTargetFolder = new SimpleStringProperty("");

    /**
     * 映射XML文件包名
     */
    private final StringProperty mappingXMLPackage = new SimpleStringProperty("");

    /**
     * 映射XML文件存放目录
     */
    private final StringProperty mappingXMLTargetFolder = new SimpleStringProperty("");

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

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenericConfiguration{");
        sb.append("name=").append(name);
        sb.append(", connectorJarPath='").append(connectorJarPath).append('\'');
        sb.append(", projectFolder=").append(projectFolder.get());
        sb.append(", modelPackage=").append(modelPackage.get());
        sb.append(", parentPackage=").append(parentPackage.get());
        sb.append(", modelPackageTargetFolder=").append(modelPackageTargetFolder.get());
        sb.append(", daoPackage=").append(daoPackage.get());
        sb.append(", daoTargetFolder=").append(daoTargetFolder.get());
        sb.append(", mappingXMLPackage=").append(mappingXMLPackage.get());
        sb.append(", mappingXMLTargetFolder=").append(mappingXMLTargetFolder.get());
        sb.append('}');
        return sb.toString();
    }

    public Path getEntityTargetDirectory() {
        return Path.of(getProjectFolder().replace(".", "/"), parentPackage.get().replace(".", "/"), modelPackage.get().replace(".", "/"));
    }

    public Path getMapperTargetDirectory() {
        return Path.of(projectFolder.get().replace(".", "/"), parentPackage.get().replace(".", "/"), daoPackage.get().replace(".", "/"));
    }

    public Path getMappingXMLTargetDirectory() {
        return Path.of(projectFolder.get().replace(".", "/"), parentPackage.get().replace(".", "/"), mappingXMLPackage.get().replace(".", "/"));
    }
}
