package org.lancoo.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JVMInfo {
    private MemoryUsageInfo heapMemoryUsage; // 堆内存使用情况
    private MemoryUsageInfo nonHeapMemoryUsage; // 非堆内存使用情况
    private List<GarbaceCollectorInfo> garbageCollectors; // 垃圾收集器信息
    private List<MemoryPoolInfo> memoryPools; // 内存池信息
}

