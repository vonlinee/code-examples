package io.devpl.codegen.mbg;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;

public class A {

    // 要生成的表名
    private static final String[] tableNamesToBeGenerated = {
            "resource_auth",
            "resource_base",
            "resource_cache",
            "resource_cdn",
            "resource_database",
            "resource_disk",
            "resource_dms",
            "resource_dns",
            "resource_ecs",
            "resource_eip",
            "resource_elb",
            "resource_im",
            "resource_mq",
            "resource_obs",
            "resource_rtc",
            "resource_sms",
            "resource_ssl",
            "resource_video_live"};

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/cloud_resource_management?useUnicode=true&characterEncoding=UTF-8&useSSL=false&&serverTimezone=GMT%2B8", "root", "123456")
                         .globalConfig(builder -> {
                             builder.author("wangliang") // 设置作者
                                    .fileOverride()
                                    .enableSwagger() // 开启 swagger 模式
                                    .outputDir("D:\\Work\\Code\\CloudResMgt-java\\src\\main\\java"); // 指定输出目录
                         })
                         .packageConfig(builder -> {
                             builder.parent("com.lancoo.cloudresource") // 设置父包名
                                    .moduleName("") // 设置父包模块名
                                    .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\Work\\Code\\CloudResMgt-java\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                         })
                         .strategyConfig(builder -> {
                             builder.entityBuilder().enableFileOverride();
                             builder.mapperBuilder().enableFileOverride();
                             builder.addInclude(Arrays.asList(tableNamesToBeGenerated)); // 设置需要生成的表名
                             builder.mapperBuilder().enableBaseResultMap();
                         })
                         .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                         .execute();
    }
}
