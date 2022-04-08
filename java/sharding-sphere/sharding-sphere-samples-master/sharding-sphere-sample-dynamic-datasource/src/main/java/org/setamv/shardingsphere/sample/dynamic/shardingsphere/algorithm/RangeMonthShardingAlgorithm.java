package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 基于月的范围分片算法。分片后缀：yyyy_mm，如：2021_02
 * @author setamv
 * @date 2021-04-10 10:00:00
 */
public class RangeMonthShardingAlgorithm<T extends Comparable<?>> implements RangeShardingAlgorithm<T> {

    private static final Logger logger = LoggerFactory.getLogger(RangeMonthShardingAlgorithm.class);

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<T> shardingValue) {
        Set<String> matchedTargetNames = new HashSet<>();
        T lowerEndPoint = shardingValue.getValueRange().lowerEndpoint();
        T upperEndPoint = shardingValue.getValueRange().upperEndpoint();

        YearMonth lowerYearMonth = lowerEndPoint == null ? null : YearMonth.parse(lowerEndPoint);
        YearMonth upperYearMonth = upperEndPoint == null ? null : YearMonth.parse(upperEndPoint);

        // 遍历分片的表集合，找到匹配本次分片条件的表
        for (String availableTargetName : availableTargetNames) {
            YearMonth targetYearMonth = parseYearMonthFromSuffix(availableTargetName);
            if (targetYearMonth == null) {
                continue;
            }
            // 如果目标表所在年月 处于 lowerYearMonth 和 upperYearMonth 之间，表示被分片条件命中（lowerYearMonth 或 upperYearMonth 为空表示不限定下限或上限）
            boolean lowerMatched = lowerYearMonth == null || lowerYearMonth.compareTo(targetYearMonth) <= 0;
            boolean upperMatched = upperEndPoint == null || upperYearMonth.compareTo(targetYearMonth) >= 0;
            if (lowerMatched && upperMatched) {
                matchedTargetNames.add(availableTargetName);
            }
        }

        return matchedTargetNames;
    }

    static final String YEAR_MONTH_PATTERN = "\\d{4}_\\d{2}";
    static final int SUFFIX_LENGTH = "yyyy_mm".length();

    /**
     * 从目标分片名称的后缀解析年月。
     * @param targetName 目标表名
     * @return 从表名的后缀解析年月
     */
    private YearMonth parseYearMonthFromSuffix(String targetName) {
        if (targetName == null) {
            return null;
        }
        // 判断目标分片名称的长度是否够 yyyy_mm 的长度
        if (targetName.length() <= SUFFIX_LENGTH) {
            return null;
        }
        // 获取目标分片名称的年月后缀：yyyy_mm
        int targetNameLen = targetName.length();
        String suffix = targetName.substring(targetNameLen - SUFFIX_LENGTH, targetNameLen);
        if (!suffix.matches(YEAR_MONTH_PATTERN)) {
            logger.debug("目标分片名称[{}]后缀不符合yyyy_mm格式，分片算法将忽略该表", targetName);
            return null;
        }
        String[] splitItems = suffix.split("_");
        return new YearMonth(Integer.valueOf(splitItems[0]), Integer.valueOf(splitItems[1]));
    }
}
