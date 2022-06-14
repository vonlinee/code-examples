package io.maker.base.config.spi;

import io.maker.base.config.Config;
import io.maker.base.config.ConfigFile;
import io.maker.base.config.ConfigFileFormat;

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
    ConfigFile createConfigFile(String namespace, ConfigFileFormat configFileFormat);
}