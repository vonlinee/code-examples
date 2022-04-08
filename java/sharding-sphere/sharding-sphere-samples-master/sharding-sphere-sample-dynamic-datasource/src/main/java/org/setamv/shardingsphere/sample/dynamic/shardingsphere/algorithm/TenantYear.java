package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 租户、年份对象。
 * @author setamv
 * @date 2021-04-17 10:00:00
 */
@Data
public class TenantYear {


    public static final String TENANT_YEAR_PATTERN = "\\d+_\\d{4}";

    private Long tenantId;
    private Integer year;

    public TenantYear(Long tenantId, Integer year) {
        this.tenantId = tenantId;
        this.year = year;
    }

    /**
     * 从格式化的文本中解析并构建租户、年份对象。文本
     * @param formattedText 格式化的文本，格式为：租户ID_年份，如：101_2020
     * @return 租户年份对象
     */
    public static TenantYear parse(String formattedText) {
        if (!formattedText.matches(TENANT_YEAR_PATTERN)) {
            throw new IllegalArgumentException("租户、年份文本格式错误，必须是【租户ID_年份】的格式。如：101_2020");
        }
        String[] parts = formattedText.split("_");
        Long tenantId = Long.parseLong(parts[0]);
        Integer year = Integer.parseInt(parts[1]);
        return new TenantYear(tenantId, year);
    }

    /**
     * 解析日期的年份类型并构建租户、年份对象
     * @param tenantId 租户ID
     * @param date 支持以下类型的对象：{@link Date}、{@link LocalDate}、{@link LocalDateTime}
     * @return 租户年份对象
     */
    public static TenantYear parse(Long tenantId, Object date) {
        YearMonth yearMonth = YearMonth.parse(date);
        return new TenantYear(tenantId, yearMonth.getYear());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{tenantId.hashCode(), year});
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TenantYear
                && Objects.equals(tenantId, ((TenantYear) obj).tenantId)
                && Objects.equals(year, ((TenantYear) obj).year);
    }
}
