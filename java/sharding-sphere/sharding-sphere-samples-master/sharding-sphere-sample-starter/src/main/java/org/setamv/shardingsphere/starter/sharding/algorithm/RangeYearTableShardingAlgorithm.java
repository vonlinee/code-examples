package org.setamv.shardingsphere.starter.sharding.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 基于年的分片算法。如果年份小于2020年，返回ds0；否则，返回ds1
 */
public class RangeYearTableShardingAlgorithm<T extends Comparable<?>> implements RangeShardingAlgorithm<T> {


    /**
     * 按年分片。从起始日期到截止日期之间的每一年，返回一个表的分片
     * @param availableTargetNames
     * @param shardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<T> shardingValue) {
        Set<String> shardingTables = new HashSet<>();
        Range<T> range = shardingValue.getValueRange();
        int startYear = range.hasLowerBound() ? AlgorithmUtils.parseYear(range.lowerEndpoint()) : ShardingConstant.SHARDING_YEAR_START;
        int endYear = range.hasUpperBound() ? AlgorithmUtils.parseYear(range.upperEndpoint()) : ShardingConstant.SHARDING_YEAR_END;
        for (String availableTargetName : availableTargetNames) {
            int year = parseYearFromSuffix(availableTargetName);
            if (year >= startYear && year <= endYear) {
                shardingTables.add(availableTargetName);
            }
        }
        return shardingTables;
    }

    static final int SUFFIX_LENGTH = "yyyy".length();
    static final String YEAR_PATTERN = "\\d{4}";

    /**
     * 从表名的后缀解析年月。
     * @param tableName 目标表名
     * @return 从表名的后缀解析年份
     */
    private Integer parseYearFromSuffix(String tableName) {
        if (tableName == null) {
            return null;
        }
        // 判断表名的长度是否够 yyyy_mm 的长度
        if (tableName.length() <= SUFFIX_LENGTH) {
            return null;
        }
        // 获取表名的年份后缀：yyyy
        int targetNameLen = tableName.length();
        String suffix = tableName.substring(targetNameLen - SUFFIX_LENGTH, targetNameLen);
        if (!suffix.matches(YEAR_PATTERN)) {
            throw new RuntimeException(String.format("目标表[{}]后缀不符合yyyy格式，分片算法将忽略该表", tableName));
        }
        return Integer.valueOf(suffix);
    }
}
