package io.devpl.toolkit.utils;

import com.google.common.base.Strings;
import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.dto.Constant;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.regex.Pattern;

@Getter
@Slf4j
public class ProjectPathResolver {

    /**
     * Java文件所在根目录
     */
    private String sourcePath;

    /**
     * 资源文件所在根目录
     */
    private String resourcePath;

    private String baseProjectPath;

    private final String basePackage;

    private final Pattern packagePattern = Pattern.compile("[a-zA-Z]+[0-9a-zA-Z_]*(\\.[a-zA-Z]+[0-9a-zA-Z_]*)*");

    public ProjectPathResolver(String basePackage) {
        this.basePackage = basePackage;
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        String rootPath;
        URL resource = contextLoader.getResource(".");
        if (resource != null) {
            String projectDir = resource.getPath();
            projectDir = StringUtils.utf8Decode(projectDir);
            String[] paths = projectDir.split("/");
            StringBuilder temp = new StringBuilder();
            // 去掉目录的最后两个子目录，通常是/target/classes
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (Strings.isNullOrEmpty(path)) {
                    continue;
                }
                if (i < paths.length - 2) {
                    temp.append(path);
                    temp.append(File.separator);
                }
            }
            rootPath = temp.toString();
        } else {
            rootPath = StringUtils.utf8Decode(System.getProperty("user.dir")) + File.separator;
        }
        // linux环境下识别项目根目录缺少“/”的问题
        if (!Runtime.isWindows() && !rootPath.startsWith("/")) {
            rootPath = "/" + rootPath;
        }
        refreshBaseProjectPath(rootPath);
    }

    /**
     * 将文件输出的包名转换为绝对路径
     */
    public String convertPackageToPath(String packageName) {
        if (StringUtils.hasLength(packageName)) {
            throw new BusinessException("包名为空");
        }
        boolean isResourceFile = false;
        if (packageName.startsWith(Constant.PACKAGE_RESOURCES_PREFIX)) {
            packageName = packageName.replaceFirst(Constant.PACKAGE_RESOURCES_PREFIX, "");
            isResourceFile = true;
        } else if (packageName.startsWith(Constant.PACKAGE_JAVA_PREFIX)) {
            packageName = packageName.replaceFirst(Constant.PACKAGE_JAVA_PREFIX, "");
        }
        if (!packagePattern.matcher(packageName).matches()) {
            throw new BusinessException("不是合法的包名：" + packageName);
        }
        String[] folders = packageName.split("\\.");
        StringBuilder path;
        if (isResourceFile) {
            path = new StringBuilder(resourcePath);
        } else {
            path = new StringBuilder(sourcePath);
        }
        for (String folder : folders) {
            path.append(File.separator).append(folder);
        }
        return path.toString();
    }

    public String convertPathToPackage(String path) {
        if (path.startsWith(sourcePath)) {
            path = path.replace(sourcePath, "");
        } else if (path.startsWith(resourcePath)) {
            path = path.replace(resourcePath, "");
        } else {
            throw new BusinessException("无法将该路径转换为包名：" + path);
        }
        String packageStr = path.replace(File.separator, ".");
        if (packageStr.startsWith(".")) {
            packageStr = packageStr.substring(1);
        }
        return packageStr;
    }

    public String resolveEntityPackage() {
        return PathUtils.joinPackage(basePackage, "entity");
    }

    public String resolveControllerPackage() {
        return PathUtils.joinPackage(basePackage, "controller");
    }

    public String resolveServicePackage() {
        return PathUtils.joinPackage(basePackage, "service");
    }

    public String resolveServiceImplPackage() {
        return PathUtils.joinPackage(basePackage, "service", "impl");
    }

    public String resolveMapperPackage() {
        return PathUtils.joinPackage(basePackage, "mapper");
    }

    public synchronized void refreshBaseProjectPath(String rootPath) {
        if (baseProjectPath == null || !baseProjectPath.equals(rootPath)) {
            this.baseProjectPath = rootPath;
            sourcePath = new File(baseProjectPath + "src/main/java".replace("/", File.separator)).toString();
            resourcePath = new File(baseProjectPath + "src/main/resources".replace("/", File.separator)).toString();
        }
    }
}
