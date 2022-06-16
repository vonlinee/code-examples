package io.maker.base.utils.config.internal;

import java.util.Properties;

public interface RepositoryChangeListener {
    /**
     * Invoked when config repository changes.
     *
     * @param namespace     the namespace of this repository change
     * @param newProperties the properties after change
     */
    void onRepositoryChange(String namespace, Properties newProperties);
}