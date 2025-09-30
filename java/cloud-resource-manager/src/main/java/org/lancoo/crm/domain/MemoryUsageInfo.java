package org.lancoo.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoryUsageInfo {
    private String initial; // 初始内存
    private String used; // 已用内存
    private String committed; // 提交内存
    private String max; // 最大内存
}
