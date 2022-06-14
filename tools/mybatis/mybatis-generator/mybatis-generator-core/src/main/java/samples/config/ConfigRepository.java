package samples.config;

import java.util.Map;

/**
 * 配置只支持一对一的KV结构
 */
public interface ConfigRepository<V> extends Map<String, V> {
    String name();
}

