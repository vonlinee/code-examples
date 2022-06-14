package io.maker.base.config.internal;

import io.maker.base.config.ConfigUtils;

import java.util.Properties;

public class DefaultPropertiesFactory implements PropertiesFactory {

    private ConfigUtils m_configUtil;

    public DefaultPropertiesFactory() {
    }

    @Override
    public Properties getPropertiesInstance() {
        if (m_configUtil.isPropertiesOrderEnabled()) {
            return new OrderedProperties();
        } else {
            return new Properties();
        }
    }
}