package org.example.agentdemo.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.agentdemo.mapper.StudentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    StudentMapper studentMapper;

    @Resource
    SqlSessionFactory sqlSessionFactory;

    @GetMapping(value = "/hotswap")
    public String test(Map<String, Object> param) {
        MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement("org.example.agentdemo.mapper.StudentMapper.queryAll");
        BoundSql boundSql = ms.getBoundSql(param);
        return boundSql.getSql();
    }

    @GetMapping(value = "/test2")
    public String test2(Map<String, Object> param) {

        Configuration configuration = sqlSessionFactory.getConfiguration();
        Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
        for (MappedStatement mappedStatement : mappedStatements) {
            System.out.println(mappedStatement.getId());

            BoundSql boundSql = mappedStatement.getBoundSql(new HashMap<String, Object>());
            String sql = boundSql.getSql();
            System.out.println(sql);
        }

        MybatisConfiguration config;



        return sqlSessionFactory.getConfiguration().toString();
    }
}
