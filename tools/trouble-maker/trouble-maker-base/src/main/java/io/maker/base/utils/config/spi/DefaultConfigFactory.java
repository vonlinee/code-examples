package io.maker.base.utils.config.spi;

import io.maker.base.config.*;
import io.maker.base.utils.config.ConfigFile;
import io.maker.base.utils.config.ConfigFileType;
import io.maker.base.utils.config.internal.ConfigRepository;
import io.maker.base.utils.config.Config;
import io.maker.base.utils.config.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation of {@link ConfigFactory}.
 * <p>
 * Supports namespaces of format:
 * <ul>
 *   <li>{@link ConfigFileType#Properties}</li>
 *   <li>{@link ConfigFileType#XML}</li>
 *   <li>{@link ConfigFileType#JSON}</li>
 *   <li>{@link ConfigFileType#YML}</li>
 *   <li>{@link ConfigFileType#YAML}</li>
 *   <li>{@link ConfigFileType#TXT}</li>
 * </ul>
 *
 * @author Jason Song(song_s@ctrip.com)
 * @author Diego Krupitza(info@diegokrupitza.com)
 */
public class DefaultConfigFactory implements ConfigFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultConfigFactory.class);
    private ConfigUtils m_configUtil;

    public DefaultConfigFactory() {

    }

    @Override
    public Config create(String namespace) {
        ConfigFileType format = determineFileFormat(namespace);

        ConfigRepository configRepository = null;
        // although ConfigFileType.Properties are compatible with themselves we
        // should not create a PropertiesCompatibleFileConfigRepository for them
        // calling the method `createLocalConfigRepository(...)` is more suitable
        // for ConfigFileType.Properties
        if (ConfigFileType.isPropertiesCompatible(format) &&
                format != ConfigFileType.Properties) {
            //configRepository = createPropertiesCompatibleFileConfigRepository(namespace, format);
        } else {
            //configRepository = createConfigRepository(namespace);
        }

        logger.debug("Created a configuration repository of type [{}] for namespace [{}]",
                configRepository.getClass().getName(), namespace);
        return this.createRepositoryConfig(namespace, configRepository);
    }

    @Override
    public ConfigFile createConfigFile(String namespace, ConfigFileType configFileFormat) {
        return null;
    }

    protected Config createRepositoryConfig(String namespace, ConfigRepository configRepository) {
        //return new DefaultConfig(namespace, configRepository);
        return null;
    }

    // for namespaces whose format are not properties, the file extension must be present, e.g. application.yaml
    ConfigFileType determineFileFormat(String namespaceName) {
        String lowerCase = namespaceName.toLowerCase();
        for (ConfigFileType format : ConfigFileType.values()) {
            if (lowerCase.endsWith("." + format.getValue())) {
                return format;
            }
        }
        return ConfigFileType.Properties;
    }

    String trimNamespaceFormat(String namespaceName, ConfigFileType format) {
        String extension = "." + format.getValue();
        if (!namespaceName.toLowerCase().endsWith(extension)) {
            return namespaceName;
        }

        return namespaceName.substring(0, namespaceName.length() - extension.length());
    }
}
