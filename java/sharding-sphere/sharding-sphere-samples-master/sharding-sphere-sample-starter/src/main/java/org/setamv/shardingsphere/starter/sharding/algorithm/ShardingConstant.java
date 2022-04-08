/*
 * 〈描述〉
 *
 * @author luowei
 * @since 版本号
 */

package org.setamv.shardingsphere.starter.sharding.algorithm;

import java.time.LocalDate;

/**
 * 分片的常量值
 * @author setamv
 * @since 版本号
 */
public interface ShardingConstant {

    /**
     * 分片的起始年份
     */
    int SHARDING_YEAR_START = 2018;

    /**
     * 分片的截止年份
     */
    int SHARDING_YEAR_END = 2021;
}
