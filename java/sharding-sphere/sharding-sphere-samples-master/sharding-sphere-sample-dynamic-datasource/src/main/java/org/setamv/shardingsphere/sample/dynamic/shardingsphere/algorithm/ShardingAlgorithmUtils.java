package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分片算法的工具类
 * @author setamv
 * @date 2021-04-16
 */
public class ShardingAlgorithmUtils {

    private static final Logger logger = LoggerFactory.getLogger(ShardingAlgorithmUtils.class);

    private static final String YEAR_PATTERN = "\\d{4}";
    private static final int YEAR_SUFFIX_LENGTH = "yyyy".length();
    private static final String TENANT_YEAR_PATTERN = "\\d{3}_\\d{4}";
    private static final int TENANT_YEAR_SUFFIX_LENGTH = "xxx_yyyy".length();

    private ShardingAlgorithmUtils() {

    }

    /**
     * 从目标名称的后缀解析年份。
     * @param targetName 目标表名
     * @return 从表名的后缀解析年份。如果没有，返回null
     */
    public static Integer parseYearFromSuffix(String targetName) {
        if (targetName == null) {
            return null;
        }
        // 判断表名的长度是否够 yyyy_mm 的长度
        if (targetName.length() <= YEAR_SUFFIX_LENGTH) {
            return null;
        }
        // 获取表名的年份后缀：yyyy
        int targetNameLen = targetName.length();
        String suffix = targetName.substring(targetNameLen - YEAR_SUFFIX_LENGTH, targetNameLen);
        if (!suffix.matches(YEAR_PATTERN)) {
            logger.debug("目标名称[{}]后缀不符合yyyy格式，分片算法将忽略该表", targetName);
            return null;
        }
        return Integer.parseInt(suffix);
    }

    /**
     * 从目标名称的后缀解析租户ID和年份。租户ID长度为3为数字，年份为4为数字，如：101_2020
     * @param targetName 目标表名
     * @return 从表名的后缀解析年份。如果没有，返回null
     */
    public static String cutTenantYearFromSuffix(String targetName) {
        if (targetName == null) {
            return null;
        }
        // 判断表名的长度是否够 yyyy_mm 的长度
        if (targetName.length() <= TENANT_YEAR_SUFFIX_LENGTH) {
            return null;
        }
        // 获取表名的租户ID_年后缀：xxx_yyyy
        int targetNameLen = targetName.length();
        String suffix = targetName.substring(targetNameLen - TENANT_YEAR_SUFFIX_LENGTH, targetNameLen);
        if (!suffix.matches(TENANT_YEAR_PATTERN)) {
            logger.debug("目标名称[{}]后缀不符合“租户ID_yyyy”格式，分片算法将忽略该表", targetName);
            return null;
        }
        return suffix;
    }
}
