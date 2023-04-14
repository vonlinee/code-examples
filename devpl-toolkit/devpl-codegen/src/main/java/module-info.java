module devpl.codegen {
    requires org.slf4j;
    requires java.sql;
    requires devpl.sdk.internal;
    requires com.baomidou.mybatis.plus.annotation;
    requires spring.jdbc;
    requires spring.core;
    requires spring.tx;
    requires freemarker;
    requires com.alibaba.fastjson2;
    requires druid;
    requires lombok;
    requires jsqlparser;
    requires org.apache.commons.text;
    requires spring.beans;
    requires com.github.javaparser.core;
    requires org.jetbrains.annotations;
    requires velocity.engine.core;
    // mybatis 生成器
    requires org.mybatis.generator;

    requires org.mybatis;

    exports io.devpl.codegen;
    exports io.devpl.codegen.jdbc.dialect.mysql;
    exports io.devpl.codegen.sql;
    exports io.devpl.codegen.jdbc.meta;
    exports io.devpl.codegen.mbpg.config;
    exports io.devpl.codegen.generator.template;
    exports io.devpl.codegen.mbpg.config.querys;
    exports io.devpl.codegen.mbpg.config.converts;
    exports io.devpl.codegen.keywords;
    exports io.devpl.codegen.mbpg;
    exports io.devpl.codegen.mbpg.config.po;
    exports io.devpl.codegen.generator.template.impl;
    exports io.devpl.codegen.mbpg.config.rules;
    exports io.devpl.codegen.api;
    exports io.devpl.codegen.utils;
    exports io.devpl.codegen.generator;
    exports io.devpl.codegen.jdbc;
}