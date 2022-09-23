package io.devpl.spring.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 控制哪些自动配置类被import
 * 功能描述：通过全限定类名过滤自动配置类
 */
@Slf4j
public class DevplAutoConfigurationImportFilter implements AutoConfigurationImportFilter {

    private final Map<String, Boolean> filter = new LinkedHashMap<>();

    public DevplAutoConfigurationImportFilter() {
        filter.put("org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration", false);
    }

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
