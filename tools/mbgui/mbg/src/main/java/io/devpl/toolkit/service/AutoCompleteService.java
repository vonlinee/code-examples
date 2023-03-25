package io.devpl.toolkit.service;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Sets;
import io.devpl.toolkit.utils.ProjectPathResolver;
import io.devpl.toolkit.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static io.devpl.toolkit.dto.Constant.DOT_XML;

/**
 * 自动提示
 */
@Service
public class AutoCompleteService {

    private final ProjectPathResolver projectPathResolver = new ProjectPathResolver("com.example");

    public Set<String> searchXmlMapperName(String mapperLocationPrefix, String searchKey) {
        String mapperRootPath = "";
        Set<String> hitNames = Sets.newHashSet();
        doSearch(new File(mapperRootPath), DOT_XML, searchKey, hitNames);
        return hitNames;
    }

    private void doSearch(File rootDir, String searchKey, String suffix, Set<String> hitNames) {
        Path path = rootDir.toPath();
        if (!Files.exists(path)) {
            return;
        }
        if (!Files.isDirectory(path)) {
            return;
        }
        File[] files = FileUtil.ls(rootDir.getAbsolutePath());
        for (File file : files) {
            if (!file.isDirectory()) {
                String filePackageName = projectPathResolver.convertPathToPackage(file.getAbsolutePath());
                if (match(filePackageName, searchKey, suffix)) {
                    hitNames.add(filePackageName.substring(0, filePackageName.length() - suffix.length()));
                }
            } else {
                doSearch(file, suffix, searchKey, hitNames);
            }
        }
    }

    private boolean match(String name, String searchKey, String suffix) {
        if (!name.endsWith(suffix)) {
            return false;
        }
        if (StringUtils.isNullOrEmpty(name)) {
            return true;
        }
        return StringUtils.containsIgnoreCase(name, searchKey);
    }
}
