package org.lancoo.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoryPoolInfo {
    private String name; // 内存池名称
    private MemoryUsageInfo usage; // 内存使用情况
}