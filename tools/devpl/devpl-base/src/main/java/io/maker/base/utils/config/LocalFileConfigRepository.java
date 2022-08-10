package io.maker.base.utils.config;

import com.google.common.base.Joiner;
import io.maker.base.utils.SystemRuntime;
import io.maker.base.utils.Validator;
import io.maker.base.utils.config.internal.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class LocalFileConfigRepository extends AbstractConfigRepository
        implements RepositoryChangeListener {
    private static final Logger logger = LoggerFactory.getLogger(LocalFileConfigRepository.class);
    private static final String CONFIG_DIR = "/config-cache";
    private final String m_namespace;
    private File m_baseDir;
    private final ConfigUtils m_configUtil;
    private volatile Properties m_fileProperties;
    private volatile ConfigRepository m_upstream;

    private volatile ConfigSourceType m_sourceType = ConfigSourceType.LOCAL;

    protected PropertiesFactory propertiesFactory = new DefaultPropertiesFactory();

    /**
     * Constructor.
     *
     * @param namespace the namespace
     */
    public LocalFileConfigRepository(String namespace) {
        this(namespace, null);
    }

    public LocalFileConfigRepository(String namespace, ConfigRepository upstream) {
        m_namespace = namespace;
        m_configUtil = new ConfigUtils();
        this.setLocalCacheDir(findLocalCacheDir(), false);
        this.setUpstreamRepository(upstream);
        this.trySync();
    }

    void setLocalCacheDir(File baseDir, boolean syncImmediately) {
        m_baseDir = baseDir;
        this.checkLocalConfigCacheDir(m_baseDir);
        if (syncImmediately) {
            this.trySync();
        }
    }

    private File findLocalCacheDir() {
        try {
            String defaultCacheDir = "";
            Path path = Paths.get(defaultCacheDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            if (Files.exists(path) && Files.isWritable(path)) {
                return new File(defaultCacheDir, CONFIG_DIR);
            }
        } catch (Throwable ex) {
            //ignore
        }
        return new File(SystemRuntime.getClassPath(), CONFIG_DIR);
    }

    @Override
    public Properties getConfig() {
        if (m_fileProperties == null) {
            sync();
        }
        Properties result = propertiesFactory.getPropertiesInstance();
        result.putAll(m_fileProperties);
        return result;
    }

    @Override
    public void setUpstreamRepository(ConfigRepository upstreamConfigRepository) {
        if (upstreamConfigRepository == null) {
            return;
        }
        //clear previous listener
        if (m_upstream != null) {
            m_upstream.removeChangeListener(this);
        }
        m_upstream = upstreamConfigRepository;
        upstreamConfigRepository.addChangeListener(this);
    }

    @Override
    public ConfigSourceType getSourceType() {
        return m_sourceType;
    }

    @Override
    public void onRepositoryChange(String namespace, Properties newProperties) {

    }

    @Override
    protected void sync() {

    }

    private boolean trySyncFromUpstream() {
        if (m_upstream == null) {
            return false;
        }
        try {
            updateFileProperties(m_upstream.getConfig(), m_upstream.getSourceType());
            return true;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private synchronized void updateFileProperties(Properties newProperties, ConfigSourceType sourceType) {
        this.m_sourceType = sourceType;
        if (newProperties.equals(m_fileProperties)) {
            return;
        }
        this.m_fileProperties = newProperties;
        persistLocalCacheFile(m_baseDir, m_namespace);
    }

    private Properties loadFromLocalCacheFile(File baseDir, String namespace) throws IOException {
        Validator.whenNull(baseDir, "Basedir cannot be null");
        File file = assembleLocalCacheFile(baseDir, namespace);
        Properties properties = null;
        if (file.isFile() && file.canRead()) {
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                properties = propertiesFactory.getPropertiesInstance();
                properties.load(in);
                logger.debug("Loading local config file {} successfully!", file.getAbsolutePath());
            } catch (IOException ex) {
                throw new RuntimeException(String.format("Loading config from local cache file %s failed", file.getAbsolutePath()), ex);
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                    // ignore
                }
            }
        } else {
            throw new RuntimeException(
                    String.format("Cannot read from local cache file %s", file.getAbsolutePath()));
        }
        return properties;
    }

    void persistLocalCacheFile(File baseDir, String namespace) {

    }

    private void checkLocalConfigCacheDir(File baseDir) {

    }

    File assembleLocalCacheFile(File baseDir, String namespace) {
        String fileName = String.format("%s.properties", Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
                .join("", m_configUtil.getCluster(), namespace));
        return new File(baseDir, fileName);
    }
}
