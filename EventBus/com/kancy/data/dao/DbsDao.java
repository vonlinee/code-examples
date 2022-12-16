package com.kancy.data.dao;

import lombok.extern.slf4j.Slf4j;
import com.kancy.data.entity.Dbs;
import com.kancy.data.mapper.DbsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * (dbs)数据DAO
 *
 * @author kancy
 * @since 2022-12-16 09:30:28
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Repository
public class DbsDao extends ServiceImpl<DbsMapper, Dbs> {

}