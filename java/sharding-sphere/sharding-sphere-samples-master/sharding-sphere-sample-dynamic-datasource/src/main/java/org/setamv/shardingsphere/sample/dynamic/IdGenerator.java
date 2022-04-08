package org.setamv.shardingsphere.sample.dynamic;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * ID生成器
 * @author setamv
 * @date 2021-04-17
 */
public class IdGenerator {

    private static final SnowflakeShardingKeyGenerator ID_GENERATOR = new SnowflakeShardingKeyGenerator();

    private IdGenerator() {

    }

    /**
     * 生成ID
     * @return ID
     */
    public static Long generateId() {
        return (Long)ID_GENERATOR.generateKey();
    }

    /**
     * 批量生成ID
     * @param size 生成ID的数量
     * @return 批量生成的ID
     */
    public static List<Long> generateIds(int size) {
        List<Long> ids = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ids.add((Long)ID_GENERATOR.generateKey());
        }
        return ids;
    }
}
