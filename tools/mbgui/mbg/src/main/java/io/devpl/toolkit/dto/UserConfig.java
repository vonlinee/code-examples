package io.devpl.toolkit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;
import io.devpl.toolkit.strategy.*;
import io.devpl.toolkit.utils.CollectionUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 配置项
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserConfig {

    private List<OutputFileInfo> outputFiles;

    /**
     * 实体类策略配置
     */
    private EntityStrategy entityStrategy = new EntityStrategy();

    /**
     * Mapper策略配置
     */
    private MapperStrategy mapperStrategy = new MapperStrategy();

    /**
     * Mapper XML 策略配置
     */
    private MapperXmlStrategy mapperXmlStrategy = new MapperXmlStrategy();

    private ControllerStrategy controllerStrategy = new ControllerStrategy();

    private ServiceStrategy serviceStrategy = new ServiceStrategy();

    private ServiceImplStrategy serviceImplStrategy = new ServiceImplStrategy();

    @JsonIgnore
    public OutputFileInfo getControllerInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream()
                .filter((f -> Constant.FILE_TYPE_CONTROLLER.equals(f.getFileType())))
                .findFirst()
                .get();
    }

    @JsonIgnore
    public OutputFileInfo getEntityInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> Constant.FILE_TYPE_ENTITY.equals(f.getFileType()))).findFirst().get();
    }

    @JsonIgnore
    public OutputFileInfo getMapperInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> Constant.FILE_TYPE_MAPPER.equals(f.getFileType()))).findFirst().get();
    }

    @JsonIgnore
    public OutputFileInfo getMapperXmlInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream()
                .filter((f -> Constant.FILE_TYPE_MAPPER_XML.equals(f.getFileType())))
                .findFirst()
                .get();
    }

    @JsonIgnore
    public OutputFileInfo getServiceInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream().filter((f -> Constant.FILE_TYPE_SERVICE.equals(f.getFileType()))).findFirst().get();
    }

    @JsonIgnore
    public OutputFileInfo getServiceImplInfo() {
        if (outputFiles == null) {
            return null;
        }
        return outputFiles.stream()
                .filter((f -> Constant.FILE_TYPE_SERVICEIMPL.equals(f.getFileType())))
                .findFirst()
                .get();
    }

    /**
     * 从另一个项目配置中合并可修改的配置项
     *
     * @param sourceUserConfig
     * @param sourceProjectConfigPath
     * @param targetProjectConfigPath
     */
    public void merge(UserConfig sourceUserConfig, String sourceProjectConfigPath, String targetProjectConfigPath) {
        this.controllerStrategy = sourceUserConfig.getControllerStrategy();
        this.entityStrategy = sourceUserConfig.getEntityStrategy();
        this.mapperStrategy = sourceUserConfig.getMapperStrategy();
        this.mapperXmlStrategy = sourceUserConfig.getMapperXmlStrategy();
        this.serviceStrategy = sourceUserConfig.getServiceStrategy();
        this.serviceImplStrategy = sourceUserConfig.getServiceImplStrategy();

        List<OutputFileInfo> files = sourceUserConfig.getOutputFiles();
        Map<String, OutputFileInfo> map = CollectionUtils.toMap(files, OutputFileInfo::getFileType);




        changeTplPath(map.get(Constant.FILE_TYPE_SERVICEIMPL), this.getControllerInfo(), sourceProjectConfigPath, targetProjectConfigPath);
        changeTplPath(map.get(Constant.FILE_TYPE_SERVICEIMPL), this.getEntityInfo(), sourceProjectConfigPath, targetProjectConfigPath);
        changeTplPath(map.get(Constant.FILE_TYPE_SERVICEIMPL), this.getMapperInfo(), sourceProjectConfigPath, targetProjectConfigPath);
        changeTplPath(map.get(Constant.FILE_TYPE_SERVICEIMPL), this.getMapperXmlInfo(), sourceProjectConfigPath, targetProjectConfigPath);
        changeTplPath(map.get(Constant.FILE_TYPE_SERVICEIMPL), this.getServiceInfo(), sourceProjectConfigPath, targetProjectConfigPath);


        changeTplPath(map.get(Constant.FILE_TYPE_SERVICEIMPL), this.getServiceImplInfo(), sourceProjectConfigPath, targetProjectConfigPath);
    }

    private void changeTplPath(OutputFileInfo source, OutputFileInfo dist, String sourceProjectConfigPath, String targetProjectConfigPath) {
        if (source == null || Strings.isNullOrEmpty(source.getTemplatePath())) {
            return;
        }
        dist.setTemplatePath(source.getTemplatePath().replace(sourceProjectConfigPath, targetProjectConfigPath));
    }
}
