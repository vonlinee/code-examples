package io.maker.base.config.spi;

import io.maker.base.config.*;
import io.maker.base.config.internal.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation of {@link ConfigFactory}.
 * <p>
 * Supports namespaces of format:
 * <ul>
 *   <li>{@link ConfigFileFormat#Properties}</li>
 *   <li>{@link ConfigFileFormat#XML}</li>
 *   <li>{@link ConfigFileFormat#JSON}</li>
 *   <li>{@link ConfigFileFormat#YML}</li>
 *   <li>{@link ConfigFileFormat#YAML}</li>
 *   <li>{@link ConfigFileFormat#TXT}</li>
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
        ConfigFileFormat format = determineFileFormat(namespace);

        ConfigRepository configRepository = null;
        // although ConfigFileFormat.Properties are compatible with themselves we
        // should not create a PropertiesCompatibleFileConfigRepository for them
        // calling the method `createLocalConfigRepository(...)` is more suitable
        // for ConfigFileFormat.Properties
        if (ConfigFileFormat.isPropertiesCompatible(format) &&
                format != ConfigFileFormat.Properties) {
            //configRepository = createPropertiesCompatibleFileConfigRepository(namespace, format);
        } else {
            //configRepository = createConfigRepository(namespace);
        }

        logger.debug("Created a configuration repository of type [{}] for namespace [{}]",
                configRepository.getClass().getName(), namespace);
        return this.createRepositoryConfig(namespace, configRepository);
    }

    @Override
    public ConfigFile createConfigFile(String namespace, ConfigFileFormat configFileFormat) {
        return null;
    }

    protected Config createRepositoryConfig(String namespace, ConfigRepository configRepository) {
        //return new DefaultConfig(namespace, configRepository);
        return null;
    }

    // for namespaces whose format are not properties, the file extension must be present, e.g. application.yaml
    ConfigFileFormat determineFileFormat(String namespaceName) {
        String lowerCase = namespaceName.toLowerCase();
        for (ConfigFileFormat format : ConfigFileFormat.values()) {
            if (lowerCase.endsWith("." + format.getValue())) {
                return format;
            }
        }
        return ConfigFileFormat.Properties;
    }

    String trimNamespaceFormat(String namespaceName, ConfigFileFormat format) {
        String extension = "." + format.getValue();
        if (!namespaceName.toLowerCase().endsWith(extension)) {
            return namespaceName;
        }

        return namespaceName.substring(0, namespaceName.length() - extension.length());
    }
}
