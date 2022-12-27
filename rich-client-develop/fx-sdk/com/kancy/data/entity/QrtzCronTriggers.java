package com.kancy.data.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Cron类型的触发器表(qrtz_cron_triggers)实体类
 *
 * @author kancy
 * @since 2022-12-24 22:34:15
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("qrtz_cron_triggers")
public class QrtzCronTriggers extends Model<QrtzCronTriggers> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 调度名称
     */
    @TableId
	private String schedName;
    /**
     * qrtz_triggers表trigger_name的外键
     */
    @TableId
	private String triggerName;
    /**
     * qrtz_triggers表trigger_group的外键
     */
    @TableId
	private String triggerGroup;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 时区
     */
    private String timeZoneId;

}