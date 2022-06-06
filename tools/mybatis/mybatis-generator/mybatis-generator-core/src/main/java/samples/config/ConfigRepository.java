package samples.config;

import java.util.Map;

/**
 * 配置只支持一对一的KV结构
 */
public interface ConfigRepository extends Map<String, Object> {

    void initialize();

    void refresh();

    String name();

    <V> Map<String, V> getConfig();
}
