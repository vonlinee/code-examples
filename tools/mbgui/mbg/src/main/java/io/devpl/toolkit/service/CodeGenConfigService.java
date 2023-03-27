package io.devpl.toolkit.service;

import cn.hutool.core.io.FileUtil;
import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.dto.Constant;
import io.devpl.toolkit.dto.OutputFileInfo;
import io.devpl.toolkit.dto.UserConfig;
import io.devpl.toolkit.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
    private String storeDir;

    private String userConfigPath;

    @PostConstruct
    public void init() {
        this.storeDir = StringUtils.join(File.separator, false, CONFIG_HOME);
        this.userConfigPath = this.storeDir + File.separator + "user-config.json";
    }

    public String getTemplateStoreDir() {
        return PathUtils.join(this.storeDir, "template");
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

    public UserConfig getUserConfigFromFile() {
        if (!Files.exists(Path.of(this.userConfigPath))) {
            return null;
        }
        try {
            String userConfigStr = Files.readString(Path.of(userConfigPath), StandardCharsets.UTF_8);
            return JSONUtils.parseObject(userConfigStr, UserConfig.class);
        } catch (Exception e) {
            log.error("读取用户配置文件发生错误：", e);
            return null;
        }
    }

    public void saveUserConfig(UserConfig userConfig) throws IOException {
        if (userConfig == null) {
            throw new BusinessException("不能写入空的用户配置");
        }
        File userConfigFile = new File(this.userConfigPath);
        Path path = userConfigFile.toPath();
        Files.deleteIfExists(path);
        Files.createDirectories(path.getParent());
        Path configFilePath = Files.createFile(path);
        String configStr = JSONUtils.toJSONString(userConfig);
        StreamUtils.copy(new ByteArrayInputStream(configStr.getBytes(StandardCharsets.UTF_8)), Files.newOutputStream(configFilePath));
    }

    /**
     * 上传模板
     *
     * @param file 模板文件
     * @return 返回模板文件路径
     */
    public String uploadTemplate(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileSuffix = fileName.substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String saveFileName = fileName.substring(0, fileName.lastIndexOf(fileSuffix)) + DateTimeUtils.nowDateTime();

        PathUtils.join(this.storeDir, "template");
        String savePath = PathUtils.join(getTemplateStoreDir(), saveFileName);
        log.info("模板上传路径为：{}", savePath);
        try {
            Path path = Path.of(savePath);
            if (FileUtils.createFile(path)) {
                Files.copy(file.getInputStream(), path);
            }
        } catch (IOException e) {
            throw new BusinessException("上传模板文件失败", e);
        }
        return "file:" + savePath;
    }

    public void importProjectConfig(String sourcePkg) throws IOException {
        String configHomePath = PathUtils.join(System.getProperty("user.home"), CONFIG_HOME);
        if (!FileUtil.exist(configHomePath)) {
            throw new BusinessException("配置主目录不存在：" + configHomePath);
        }
        try (Stream<Path> stream = Files.list(Path.of(configHomePath))) {
            List<Path> list = stream.collect(Collectors.toList());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        File[] files = FileUtil.ls(configHomePath);
        boolean flag = false;
        for (File file : files) {
            if (file.isDirectory() && file.getName().equals(sourcePkg)) {
                File projectConfigDir = new File(this.storeDir);
                FileUtil.copyContent(file, projectConfigDir, true);
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new BusinessException("未找到待导入的源项目配置");
        }
        String sourceProjectConfigPath = PathUtils.join(System.getProperty("user.home"), CONFIG_HOME, sourcePkg);
        String targetProjectConfigPath = this.storeDir;
        UserConfig currentUserConfig = new UserConfig();
        currentUserConfig.setOutputFiles(getBuiltInFileInfo());
        currentUserConfig.merge(this.getUserConfigFromFile(), sourceProjectConfigPath, targetProjectConfigPath);
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
