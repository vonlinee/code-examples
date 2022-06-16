package io.maker.base.utils.config.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.maker.base.utils.config.Config;
import io.maker.base.utils.config.ConfigFile;
import io.maker.base.utils.config.ConfigFileType;
import io.maker.base.utils.config.spi.ConfigFactory;
import io.maker.base.utils.config.spi.ConfigFactoryManager;
import io.maker.base.utils.config.spi.DefaultConfigFactoryManager;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigManager implements ConfigManager {
    private ConfigFactoryManager m_factoryManager;

    private Map<String, Config> m_configs = new ConcurrentHashMap<>();
    private Map<String, ConfigFile> m_configFiles = new ConcurrentHashMap<>();

    public DefaultConfigManager() {
        m_factoryManager = new DefaultConfigFactoryManager();
    }

    @Override
    public Config getConfig(String namespace) {
        Config config = m_configs.get(namespace);

        if (config == null) {
            synchronized (this) {
                config = m_configs.get(namespace);

                if (config == null) {
                    ConfigFactory factory = m_factoryManager.getFactory(namespace);

                    config = factory.create(namespace);
                    m_configs.put(namespace, config);
                }
            }
        }
        return config;
    }

    @Override
    public ConfigFile getConfigFile(String namespace, ConfigFileType configFileFormat) {
        String namespaceFileName = String.format("%s.%s", namespace, configFileFormat.getValue());
        ConfigFile configFile = m_configFiles.get(namespaceFileName);

        if (configFile == null) {
            synchronized (this) {
                configFile = m_configFiles.get(namespaceFileName);

                if (configFile == null) {
                    ConfigFactory factory = m_factoryManager.getFactory(namespaceFileName);

                    configFile = factory.createConfigFile(namespaceFileName, configFileFormat);
                    m_configFiles.put(namespaceFileName, configFile);
                }
            }
        }

        return configFile;
    }
}
