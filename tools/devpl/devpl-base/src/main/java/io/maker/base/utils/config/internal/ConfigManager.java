package io.maker.base.utils.config.internal;

import io.maker.base.utils.config.Config;
import io.maker.base.utils.config.ConfigFile;
import io.maker.base.utils.config.ConfigFileType;

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
    ConfigFile getConfigFile(String namespace, ConfigFileType configFileFormat);
}