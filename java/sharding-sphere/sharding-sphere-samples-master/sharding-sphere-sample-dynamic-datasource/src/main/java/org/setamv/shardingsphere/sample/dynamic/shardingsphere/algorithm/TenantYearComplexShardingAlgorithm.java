package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 按租户、日期的年份进行复合分片。分片算法：匹配表名后缀——租户ID_年份
 * @param <T>
 * @author setamv
 * @date 2021-04-16
 */
public class TenantYearComplexShardingAlgorithm<T extends Comparable<?>> implements ComplexKeysShardingAlgorithm<T> {

    private static final String COLUMN_TENANT_ID = "tenant_id";
    private static final String COLUMN_ORDER_DATE = "order_date";

    /**
     * 分片
     * @param availableTargetNames
     * @param shardingValue 分片键和分片值。
     *        例如，分片键为 tenant_id、order_date 组成的复合分片键：
     *        <p>当指定查询条件：tenant_id = 101 AND order_date = '2020-04-01'时，shardingValue.columnNameAndShardingValuesMap 的值为：
     *              <code>{"tenant_id": [101], "order_date": "2020-04-01"}</code>
     *        <p>当指定查询条件：tenant_id = 101 AND order_date IN ('2020-04-01', '2021-05-11')时，shardingValue.columnNameAndShardingValuesMap 的值为：
     *              <code>{"tenant_id": [101], "order_date": ["2020-04-01", "2021-05-11"]}</code>
     *        <p>当指定查询条件：tenant_id = 101 AND order_date >= '2020-04-01' AND order_date <= '2021-05-11'时，
     *           shardingValue.columnNameAndShardingValuesMap 的值为：
     *              <code>{"tenant_id": [101]}</code>
     *           shardingValue.columnNameAndRangeValuesMap 的值为：
     *              <code>{"order_date": {lowerBound:"2020-04-01", upperBound:"2021-05-11"})}</code>
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<T> shardingValue) {
        Map<String, Collection<T>> columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        Map<String, Range<T>> columnNameAndRangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
        if (columnNameAndShardingValuesMap == null || columnNameAndShardingValuesMap.isEmpty()) {
            throw new IllegalArgumentException("当前SQL没有包含分片条件：租户ID");
        }

        // 获取租户ID条件。租户ID只会有一个，不允许垮租户查询
        List<Long> tenantConditions = (List<Long>)columnNameAndShardingValuesMap.get(COLUMN_TENANT_ID);
        if (CollectionUtils.isEmpty(tenantConditions)) {
            throw new IllegalArgumentException("当前SQL没有包含分片条件：租户ID");
        }
        if (tenantConditions.size() > 1) {
            throw new IllegalArgumentException("当前SQL的【租户ID】分片条件包含多个值，不允许跨租户！");
        }
        Long tenantId = tenantConditions.get(0);

        Collection<String> matchedTargetNames = new HashSet<>(availableTargetNames.size(), 1.0F);

        // 获取精确匹配类型的日期条件：
        List dateConditions = (List)columnNameAndShardingValuesMap.get(COLUMN_ORDER_DATE);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dateConditions)) {
            // 存放预期分片后缀，availableTargetNames后缀与其中任意值匹配的
            Collection<String> expectedSuffixes = new HashSet<>();
            for (Object dateCondition : dateConditions) {
                Integer year = YearMonth.parse(dateCondition).getYear();
                String suffix = tenantId + "_" + year;
                expectedSuffixes.add(suffix);
            }
            for (String availableTargetName : availableTargetNames) {
                String targetSuffix = ShardingAlgorithmUtils.cutTenantYearFromSuffix(availableTargetName);
                if (expectedSuffixes.contains(targetSuffix)) {
                    matchedTargetNames.add(availableTargetName);
                }
            }
        }

        // 获取范围匹配类型的日期条件
        Range dateRange = columnNameAndRangeValuesMap.get(COLUMN_ORDER_DATE);
        if (dateRange != null) {
            Integer lowerYear = dateRange.lowerEndpoint() == null ? null : YearMonth.parse(dateRange.lowerEndpoint()).getYear();
            Integer upperYear = dateRange.upperEndpoint() == null ? null : YearMonth.parse(dateRange.upperEndpoint()).getYear();

            for (String availableTargetName : availableTargetNames) {
                String tenantYearSuffix = ShardingAlgorithmUtils.cutTenantYearFromSuffix(availableTargetName);
                if (tenantYearSuffix != null) {
                    TenantYear tenantYear = TenantYear.parse(tenantYearSuffix);
                    int targetYear = tenantYear.getYear();
                    // 如果目标表所在年月 处于 lowerYearMonth 和 upperYearMonth 之间，表示被分片条件命中（lowerYearMonth 或 upperYearMonth 为空表示不限定下限或上限）
                    boolean lowerMatched = lowerYear == null || lowerYear <= targetYear;
                    boolean upperMatched = upperYear == null || upperYear >= targetYear;
                    if (lowerMatched && upperMatched) {
                        matchedTargetNames.add(availableTargetName);
                    }
                }
            }
        }

        return matchedTargetNames;
    }

}
