package io.devpl.toolkit.service;

import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.dto.OutputFileInfo;
import io.devpl.toolkit.dto.UserConfig;
import io.devpl.toolkit.strategy.*;
import io.devpl.toolkit.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class OutputFileInfoService {

    @Resource
    private CodeGenConfigService userConfigStore;

    UserConfig userConfig;

    @PostConstruct
    public void init() {
        userConfig = userConfigStore.getDefaultUserConfig();
    }

    public void deleteOutputFileInfo(OutputFileInfo fileInfo) throws IOException {
        if (fileInfo.isBuiltin()) {
            throw new BusinessException("内置文件配置信息不能删除");
        }
        List<OutputFileInfo> fileInfos = userConfig.getOutputFiles();
        fileInfos.remove(fileInfo);
        userConfigStore.saveUserConfig(userConfig);
    }

    public void saveOutputFileInfo(OutputFileInfo saveFileInfo) throws IOException {
        List<OutputFileInfo> fileInfos = userConfig.getOutputFiles();
        // 替换原来的配置
        if (saveFileInfo.isBuiltin()) {
            CollectionUtils.replaceAll(fileInfos, saveFileInfo, saveFileInfo);
        } else if (fileInfos.contains(saveFileInfo)) {
            CollectionUtils.replaceAll(fileInfos, saveFileInfo, saveFileInfo);
        } else {
            fileInfos.add(saveFileInfo);
        }
        userConfigStore.saveUserConfig(userConfig);
    }

    /**
     * 保存实体配置
     *
     * @param entityStrategy 实体配置
     * @throws IOException
     */
    public void saveEntityStrategy(EntityStrategy entityStrategy) throws IOException {
        userConfig.setEntityStrategy(entityStrategy);
        userConfigStore.saveUserConfig(userConfig);
    }

    public void saveMapperXmlStrategy(MapperXmlStrategy mapperXmlStrategy) throws IOException {
        userConfig.setMapperXmlStrategy(mapperXmlStrategy);
        userConfigStore.saveUserConfig(userConfig);
    }

    public void saveMapperStrategy(MapperStrategy mapperStrategy) throws IOException {
        userConfig.setMapperStrategy(mapperStrategy);
        userConfigStore.saveUserConfig(userConfig);
    }

    public void saveControllerStrategy(ControllerStrategy controllerStrategy) throws IOException {
        userConfig.setControllerStrategy(controllerStrategy);
        userConfigStore.saveUserConfig(userConfig);
    }

    public void saveServiceStrategy(ServiceStrategy serviceStrategy) throws IOException {
        userConfig.setServiceStrategy(serviceStrategy);
        userConfigStore.saveUserConfig(userConfig);
    }

    public void saveServiceImplStrategy(ServiceImplStrategy serviceImplStrategy) throws IOException {
        userConfig.setServiceImplStrategy(serviceImplStrategy);
        userConfigStore.saveUserConfig(userConfig);
    }

    public String getOutputPkgByFileType(String fileType) {
        List<OutputFileInfo> fileInfos = userConfig.getOutputFiles();
        for (OutputFileInfo fileInfo : fileInfos) {
            if (fileInfo.getFileType().equals(fileType)) {
                return fileInfo.getOutputPackage();
            }
        }
        return null;
    }
}
