package io.devpl.sdk.beans.serialize;

import java.util.NoSuchElementException;

import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;

/**
 * Lenient deserializer that ignores unknown properties.
 */
class LenientDeserializer extends DefaultDeserializer {

    /**
     * Singleton.
     */
    public static final SerDeserializer INSTANCE = new LenientDeserializer();

    /**
     * Creates an instance.
     */
    protected LenientDeserializer() {
    }

    //-----------------------------------------------------------------------
    @Override
    public MetaProperty<?> findMetaProperty(Class<?> beanType, MetaBean metaBean, String propertyName) {
        // dynamic beans force code by exception
        try {
            return metaBean.metaProperty(propertyName);
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

}
