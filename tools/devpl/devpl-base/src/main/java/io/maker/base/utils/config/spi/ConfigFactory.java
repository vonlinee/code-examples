package io.maker.base.utils.config.spi;

import io.maker.base.utils.config.Config;
import io.maker.base.utils.config.ConfigFile;
import io.maker.base.utils.config.ConfigFileType;

public interface ConfigFactory {
    /**
     * Create the config instance for the namespace.
     *
     * @param namespace the namespace
     * @return the newly created config instance
     */
    Config create(String namespace);

    /**
     * Create the config file instance for the namespace
     *
     * @param namespace the namespace
     * @return the newly created config file instance
     */
    ConfigFile createConfigFile(String namespace, ConfigFileType configFileFormat);
}