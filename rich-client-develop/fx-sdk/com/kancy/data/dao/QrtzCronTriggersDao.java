package com.kancy.data.dao;

import lombok.extern.slf4j.Slf4j;
import com.kancy.data.entity.QrtzCronTriggers;
import com.kancy.data.mapper.QrtzCronTriggersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * Cron类型的触发器表(qrtz_cron_triggers)数据DAO
 *
 * @author kancy
 * @since 2022-12-24 22:34:15
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Repository
public class QrtzCronTriggersDao extends ServiceImpl<QrtzCronTriggersMapper, QrtzCronTriggers> {

}