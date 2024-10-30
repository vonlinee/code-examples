package org.lancoo.crm.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarbaceCollectorInfo {
    private String name; // 垃圾收集器名称
    private long collectionCount; // 收集次数
    private long collectionTime; // 收集时间
}
