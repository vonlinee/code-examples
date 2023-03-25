package io.devpl.toolkit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;
import io.devpl.toolkit.utils.StringUtils;
import lombok.Data;

import static io.devpl.toolkit.dto.Constant.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputFileInfo {

    public OutputFileInfo() {
        this(true);
    }

    public OutputFileInfo(boolean builtIn) {
        this.builtIn = builtIn;
    }

    private String fileType;

    /**
     * 文件的输出包名
     */
    private String outputLocation;

    private String templateName;

    private String templatePath;

    /**
     * 是否是系统内置的文件信息
     */
    private boolean builtIn;

    public String getOutputPackage() {
        if (StringUtils.isNullOrEmpty(outputLocation)) {
            return "";
        }
        if (outputLocation.startsWith(PACKAGE_RESOURCES_PREFIX)) {
            return outputLocation.replaceFirst(PACKAGE_RESOURCES_PREFIX, "");
        } else if (outputLocation.startsWith(PACKAGE_JAVA_PREFIX)) {
            return outputLocation.replaceFirst(PACKAGE_JAVA_PREFIX, "");
        }
        return outputLocation;
    }

    @JsonIgnore
    public String getAvailableTemplatePath() {
        if (!Strings.isNullOrEmpty(templatePath)) {
            return templatePath;
        }
        switch (fileType) {
            case FILE_TYPE_ENTITY:
                return RESOURCE_PREFIX_CLASSPATH + "codetpls/entity.java.btl";
            case FILE_TYPE_MAPPER:
                return RESOURCE_PREFIX_CLASSPATH + "codetpls/mapper.java.btl";
            case FILE_TYPE_MAPPER_XML:
                return RESOURCE_PREFIX_CLASSPATH + "codetpls/mapper.xml.btl";
            case FILE_TYPE_SERVICE:
                return RESOURCE_PREFIX_CLASSPATH + "codetpls/service.java.btl";
            case FILE_TYPE_SERVICEIMPL:
                return RESOURCE_PREFIX_CLASSPATH + "codetpls/serviceImpl.java.btl";
            case FILE_TYPE_CONTROLLER:
                return RESOURCE_PREFIX_CLASSPATH + "codetpls/controller.java.btl";
        }
        return "";
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOutputLocation() {
        return outputLocation;
    }

    public void setOutputLocation(String outputLocation) {
        this.outputLocation = outputLocation;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OutputFileInfo) {
            OutputFileInfo file = (OutputFileInfo) obj;
            if (file.getFileType() == null || this.getFileType() == null) {
                return false;
            }
            return file.getFileType().equalsIgnoreCase(this.getFileType());
        }
        return false;
    }
}
