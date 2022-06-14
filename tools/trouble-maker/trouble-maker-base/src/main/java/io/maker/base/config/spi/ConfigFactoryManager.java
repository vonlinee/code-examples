package io.maker.base.config.spi;

public interface ConfigFactoryManager {
    /**
     * Get the config factory for the namespace.
     *
     * @param namespace the namespace
     * @return the config factory for this namespace
     */
    ConfigFactory getFactory(String namespace);
}