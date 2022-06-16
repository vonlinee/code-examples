package io.maker.base.utils.config.internal;

import java.util.Properties;

public interface PropertiesFactory {

    /**
     * Configuration to keep properties order as same as line order in .yml/.yaml/.properties file.
     * enable property order
     */
    String APOLLO_PROPERTY_ORDER_ENABLE = "";

    /**
     * <pre>
     * Default implementation:
     * 1. if {@link APOLLO_PROPERTY_ORDER_ENABLE} is true return a new
     * instance of {@link com.ctrip.framework.apollo.util.OrderedProperties}.
     * 2. else return a new instance of {@link Properties}
     * </pre>
     *
     * @return
     */
    Properties getPropertiesInstance();
}