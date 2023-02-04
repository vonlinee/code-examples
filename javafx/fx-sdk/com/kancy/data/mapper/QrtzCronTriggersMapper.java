package com.kancy.data.mapper;

import com.kancy.data.entity.QrtzCronTriggers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Cron类型的触发器表(qrtz_cron_triggers)数据Mapper
 *
 * @author kancy
 * @since 2022-12-24 22:34:15
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface QrtzCronTriggersMapper extends BaseMapper<QrtzCronTriggers> {

}
