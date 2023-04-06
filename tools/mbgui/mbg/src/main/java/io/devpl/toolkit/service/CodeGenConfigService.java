package io.devpl.toolkit.service;

import cn.hutool.core.io.FileUtil;
import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.dto.Constant;
import io.devpl.toolkit.dto.OutputFileInfo;
import io.devpl.toolkit.dto.UserConfig;
import io.devpl.toolkit.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.devpl.toolkit.dto.Constant.*;

@Slf4j
@Component
public class CodeGenConfigService {

    public final static String CONFIG_HOME = ".mbg-config";

    private final String templateDirectory = "template/";

    /**
     * 存储目录
     */
    @Value("${save.parent:D:/Temp}")
    private String saveParent;

    private Path pathOfConfigFile;

    @PostConstruct
    public void init() {
        this.pathOfConfigFile = Path.of(saveParent, "user-config.json");
    }

    public UserConfig getDefaultUserConfig() {
        UserConfig userConfig = getUserConfigFromFile();
        if (userConfig == null) {
            userConfig = new UserConfig();
            List<OutputFileInfo> list = getBuiltInFileInfo();
            userConfig.setOutputFiles(list);
        }
        return userConfig;
    }

    /**
     * 读取本地配置文件保存的配置信息
     *
     * @return 配置信息
     */
    public UserConfig getUserConfigFromFile() {
        if (!Files.exists(pathOfConfigFile)) {
            return null;
        }
        try {
            String userConfigStr = Files.readString(pathOfConfigFile, StandardCharsets.UTF_8);
            return JSONUtils.parseObject(userConfigStr, UserConfig.class);
        } catch (Exception e) {
            log.error("读取用户配置文件发生错误：", e);
            return null;
        }
    }

    public void saveUserConfig(UserConfig userConfig) {
        if (userConfig == null) {
            throw new BusinessException("不能写入空的用户配置");
        }
        Path path = pathOfConfigFile;
        try {
            Files.deleteIfExists(path);
            Files.createDirectories(path.getParent());
            Path configFilePath = Files.createFile(path);
            String configStr = JSONUtils.toJSONString(userConfig);
            StreamUtils.copy(new ByteArrayInputStream(configStr.getBytes(StandardCharsets.UTF_8)), Files.newOutputStream(configFilePath));
        } catch (IOException exception) {
            throw new BusinessException("保存配置失败");
        }
    }

    public void importProjectConfig(String sourcePkg) {
        String configHomePath = PathUtils.join(System.getProperty("user.home"), CONFIG_HOME);
        if (!FileUtils.exist(configHomePath)) {
            throw new BusinessException("配置主目录不存在：" + configHomePath);
        }
        try (Stream<Path> stream = Files.list(Path.of(configHomePath))) {
            List<Path> list = stream.collect(Collectors.toList());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        boolean flag = false;
        for (File file : FileUtils.listFiles(configHomePath, true)) {
            if (file.isDirectory() && file.getName().equals(sourcePkg)) {
                File projectConfigDir = new File(this.saveParent);
                FileUtil.copyContent(file, projectConfigDir, true);
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new BusinessException("未找到待导入的源项目配置");
        }
        String sourceProjectConfigPath = PathUtils.join(System.getProperty("user.home"), CONFIG_HOME, sourcePkg);
        UserConfig currentUserConfig = new UserConfig();
        currentUserConfig.setOutputFiles(getBuiltInFileInfo());
        currentUserConfig.merge(this.getUserConfigFromFile(), sourceProjectConfigPath, this.saveParent);
        // 保存用户配置信息
        this.saveUserConfig(currentUserConfig);
    }

    public List<String> getAllSavedProject() {
        String configHomePath = PathUtils.join(System.getProperty("user.home"), CONFIG_HOME);
        if (!FileUtil.exist(configHomePath)) {
            return Collections.emptyList();
        }
        List<String> projects = new ArrayList<>();
        File[] files = FileUtil.ls(configHomePath);
        for (File file : files) {
            if (file.isDirectory()) {
                projects.add(file.getName());
            }
        }
        return projects;
    }

    /**
     * 默认的内置输出文件的信息
     * 默认的配置信息
     */
    private List<OutputFileInfo> getBuiltInFileInfo() {
        ProjectPathResolver pathResolver = new ProjectPathResolver("basePackage");
        List<OutputFileInfo> builtInFiles = new ArrayList<>();
        // Entity
        OutputFileInfo entityFile = new OutputFileInfo();
        entityFile.setFileType(FILE_TYPE_ENTITY);
        entityFile.setOutputLocation(PathUtils.joinPackage("", "entity"));
        entityFile.setTemplateName(TemplateUtil.fileType2TemplateName(entityFile.getFileType()));
        builtInFiles.add(entityFile);
        // Mapper xml
        OutputFileInfo mapperXmlFile = new OutputFileInfo();
        mapperXmlFile.setFileType(FILE_TYPE_MAPPER_XML);
        mapperXmlFile.setOutputLocation(Constant.PACKAGE_RESOURCES_PREFIX + "mapper");
        mapperXmlFile.setTemplateName(TemplateUtil.fileType2TemplateName(mapperXmlFile.getFileType()));
        builtInFiles.add(mapperXmlFile);
        // Mapper
        OutputFileInfo mapperFile = new OutputFileInfo();
        mapperFile.setFileType(FILE_TYPE_MAPPER);
        mapperFile.setOutputLocation(pathResolver.resolveMapperPackage());
        mapperFile.setTemplateName(TemplateUtil.fileType2TemplateName(mapperFile.getFileType()));
        builtInFiles.add(mapperFile);
        // Service
        OutputFileInfo serviceFile = new OutputFileInfo();
        serviceFile.setFileType(FILE_TYPE_SERVICE);
        serviceFile.setOutputLocation(pathResolver.resolveServicePackage());
        serviceFile.setTemplateName(TemplateUtil.fileType2TemplateName(serviceFile.getFileType()));
        builtInFiles.add(serviceFile);
        // Service Impl
        OutputFileInfo serviceImplFile = new OutputFileInfo();
        serviceImplFile.setFileType(FILE_TYPE_SERVICEIMPL);
        serviceImplFile.setOutputLocation(pathResolver.resolveServiceImplPackage());
        serviceImplFile.setTemplateName(TemplateUtil.fileType2TemplateName(serviceImplFile.getFileType()));
        builtInFiles.add(serviceImplFile);
        // Controller
        OutputFileInfo controllerFile = new OutputFileInfo();
        controllerFile.setFileType(FILE_TYPE_CONTROLLER);
        controllerFile.setOutputLocation(pathResolver.resolveControllerPackage());
        controllerFile.setTemplateName(TemplateUtil.fileType2TemplateName(controllerFile.getFileType()));
        builtInFiles.add(controllerFile);
        return builtInFiles;
    }
}
