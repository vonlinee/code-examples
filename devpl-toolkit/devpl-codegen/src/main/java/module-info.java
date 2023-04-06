module devpl.codegen {
    requires org.jetbrains.annotations;
    requires org.slf4j;
    requires java.sql;
    requires devpl.sdk.internal;
    requires com.baomidou.mybatis.plus.core;
    requires com.baomidou.mybatis.plus.annotation;
    requires org.mybatis;
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

    exports io.devpl.codegen;
    exports io.devpl.codegen.mbpg.jdbc.dialect.mysql;
    exports io.devpl.codegen.sql;
    exports io.devpl.codegen.mbpg.jdbc.meta;
    exports io.devpl.codegen.mbpg.util;
    exports io.devpl.codegen.mbpg.config.builder;
    exports io.devpl.codegen.mbpg.config;
    exports io.devpl.codegen.mbpg.template;
    exports io.devpl.codegen.mbpg.config.querys;
    exports io.devpl.codegen.mbpg.config.converts;
    exports io.devpl.codegen.mbpg.keywords;
    exports io.devpl.codegen.mbpg;
}