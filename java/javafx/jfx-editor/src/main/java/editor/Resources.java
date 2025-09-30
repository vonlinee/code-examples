package editor;

import java.net.MalformedURLException;
import java.net.URL;

public final class Resources {

    private static final URL CLASSPATH_ROOT = Resources.class.getClassLoader().getResource("");

    /**
     * 根据相对路径获取本项目参与打包的资源文件
     * @param relativePath 相对路径
     * @return 资源文件URL
     */
    public static URL getFromProjectClasspath(String relativePath) {
        try {
            return new URL(CLASSPATH_ROOT, relativePath);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
