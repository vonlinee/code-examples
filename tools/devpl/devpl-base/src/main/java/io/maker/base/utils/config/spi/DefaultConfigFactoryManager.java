package io.maker.base.utils.config.spi;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigFactoryManager implements ConfigFactoryManager {
    private ConfigRegistry m_registry;

    private Map<String, ConfigFactory> m_factories = Maps.newConcurrentMap();

    public DefaultConfigFactoryManager() {
        m_registry = new DefaultConfigRegistry();
    }

    @Override
    public ConfigFactory getFactory(String namespace) {
        // step 1: check hacked factory
        ConfigFactory factory = m_registry.getFactory(namespace);

        if (factory != null) {
            return factory;
        }
        // step 2: check cache
        factory = m_factories.get(namespace);

        if (factory != null) {
            return factory;
        }

        // step 4: check default config factory
        factory = new DefaultConfigFactory();
        m_factories.put(namespace, factory);
        // factory should not be null
        return factory;
    }
}
