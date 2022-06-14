package io.maker.base.config.internal;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import io.maker.base.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public abstract class AbstractConfigRepository implements ConfigRepository {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConfigRepository.class);
    private List<RepositoryChangeListener> m_listeners = new CopyOnWriteArrayList<>();
    protected PropertiesFactory propertiesFactory = new DefaultPropertiesFactory();

    protected boolean trySync() {
        try {
            sync();
            return true;
        } catch (Throwable ex) {
            logger.warn("Sync config failed, will retry. Repository {}, reason: {}", this.getClass(), ExceptionUtils
                    .getDetailMessage(ex));
        }
        return false;
    }

    protected abstract void sync();

    @Override
    public void addChangeListener(RepositoryChangeListener listener) {
        if (!m_listeners.contains(listener)) {
            m_listeners.add(listener);
        }
    }

    @Override
    public void removeChangeListener(RepositoryChangeListener listener) {
        m_listeners.remove(listener);
    }

    protected void fireRepositoryChange(String namespace, Properties newProperties) {
        for (RepositoryChangeListener listener : m_listeners) {
            try {
                listener.onRepositoryChange(namespace, newProperties);
            } catch (Throwable ex) {
                logger.error("Failed to invoke repository change listener {}", listener.getClass(), ex);
            }
        }
    }
}
