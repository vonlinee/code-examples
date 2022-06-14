package io.maker.base.config.internal;

import io.maker.base.config.Config;
import io.maker.base.config.ConfigFile;
import io.maker.base.config.ConfigFileFormat;

public interface ConfigManager {
    /**
     * Get the config instance for the namespace specified.
     *
     * @param namespace the namespace
     * @return the config instance for the namespace
     */
    Config getConfig(String namespace);

    /**
     * Get the config file instance for the namespace specified.
     *
     * @param namespace        the namespace
     * @param configFileFormat the config file format
     * @return the config file instance for the namespace
     */
    ConfigFile getConfigFile(String namespace, ConfigFileFormat configFileFormat);
}