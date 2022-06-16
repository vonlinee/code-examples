package io.maker.base.utils.config;

import io.maker.base.utils.config.model.ConfigChangeEvent;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public interface ConfigChangeListener {
    /**
     * Invoked when there is any config change for the namespace.
     *
     * @param changeEvent the event for this change
     */
    void onChange(ConfigChangeEvent changeEvent);
}
