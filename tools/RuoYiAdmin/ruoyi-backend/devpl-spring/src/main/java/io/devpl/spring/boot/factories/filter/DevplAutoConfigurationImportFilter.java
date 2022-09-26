package io.devpl.spring.boot.factories.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 控制哪些自动配置类被import
 * 功能描述：通过全限定类名过滤自动配置类
 * 过滤springboot自动配置类
 * <p>
 * springboot环境下，使用rabbitmq，引入了rabbitmq相关的类库，但是又不想使用o
 * rg.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration自动配置类，
 * 打算从数据库获取rabbitmq配置信息并通过编程的方式连接rabbitmq；该怎样禁用RabbitAutoConfiguration自动配置类？
 * 你当然可以通过以下方式禁用禁用RabbitAutoConfiguration自动配置类
 * <p>
 * 方式1：{@code @EnableAutoConfiguration(exclude=RabbitAutoConfiguration.class)}
 * 方式2：通过配置文件进行配置
 * spring:
 *   autoconfigure:
 *     exclude:
 *       - org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
 */
@Slf4j
public class DevplAutoConfigurationImportFilter implements AutoConfigurationImportFilter {

    private final Map<String, Boolean> filter = new LinkedHashMap<>();

    public DevplAutoConfigurationImportFilter() {
        filter.put("org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration", false);
    }

    /**
     * 有多少个自动配置类，此方法就会执行多少次
     *
     * @param autoConfigurationClasses
     * @param autoConfigurationMetadata
     * @return
     */
    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {

        int len = autoConfigurationClasses.length;
        if (len == 1) return new boolean[]{true};
        log.info("过滤自动配置类，初始个数 = {}", len);
        boolean[] importedFlags = new boolean[len];
        Boolean flag;
        String autoConfigurationClassName;
        for (int i = 0; i < len; i++) {
            autoConfigurationClassName = autoConfigurationClasses[i];
            flag = filter.get(autoConfigurationClassName);
            if (flag == null) {
                importedFlags[i] = true;
                if (autoConfigurationClassName != null && autoConfigurationClassName.contains("devpl")) {
                    // System.out.println("Devpl自动配置类 => " + autoConfigurationClassName);
                }
            } else {
                importedFlags[i] = flag;
                if (!flag) {
                    log.info("已排除自动配置类 => {}，如需再次引入，可使用@Import引入！", autoConfigurationClassName);
                }
            }
            flag = null;
        }
        return importedFlags;
    }
}
