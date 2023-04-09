package io.devpl.codegen.mbpg.config.builder;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.devpl.codegen.mbpg.config.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 路径信息处理
 */
class PathInfoHandler {

    /**
     * 输出文件Map
     */
    private final Map<OutputFile, String> pathInfo = new HashMap<>();

    /**
     * 输出目录
     */
    private final String outputDir;

    /**
     * 包配置信息
     */
    private final PackageConfig packageConfig;

    PathInfoHandler(GlobalConfig globalConfig, TemplateConfig templateConfig, PackageConfig packageConfig) {
        this.outputDir = globalConfig.getOutputDir();
        this.packageConfig = packageConfig;
        // 设置默认输出路径
        this.setDefaultPathInfo(globalConfig, templateConfig);
        // 覆盖自定义路径
        Map<OutputFile, String> pathInfo = packageConfig.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            this.pathInfo.putAll(pathInfo);
        }
    }

    /**
     * 设置默认输出路径
     *
     * @param globalConfig   全局配置
     * @param templateConfig 模板配置
     */
    private void setDefaultPathInfo(GlobalConfig globalConfig, TemplateConfig templateConfig) {
        putPathInfo(templateConfig.getEntity(globalConfig.isKotlin()), OutputFile.entity, ConstVal.ENTITY);
        putPathInfo(templateConfig.getMapper(), OutputFile.mapper, ConstVal.MAPPER);
        putPathInfo(templateConfig.getXml(), OutputFile.xml, ConstVal.XML);
        putPathInfo(templateConfig.getService(), OutputFile.service, ConstVal.SERVICE);
        putPathInfo(templateConfig.getServiceImpl(), OutputFile.serviceImpl, ConstVal.SERVICE_IMPL);
        putPathInfo(templateConfig.getController(), OutputFile.controller, ConstVal.CONTROLLER);
        putPathInfo(OutputFile.parent, ConstVal.PARENT);
    }

    public Map<OutputFile, String> getPathInfo() {
        return this.pathInfo;
    }

    private void putPathInfo(String template, OutputFile outputFile, String module) {
        if (StringUtils.isNotBlank(template)) {
            putPathInfo(outputFile, module);
        }
    }

    private void putPathInfo(OutputFile outputFile, String module) {
        pathInfo.putIfAbsent(outputFile, joinPath(outputDir, packageConfig.getPackageInfo(module)));
    }

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }
}
