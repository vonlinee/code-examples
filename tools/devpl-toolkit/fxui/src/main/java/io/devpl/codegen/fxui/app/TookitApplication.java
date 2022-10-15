package io.devpl.codegen.fxui.app;

import io.devpl.codegen.fxui.utils.Utils;

public class TookitApplication {


    public static void main(String[] args) {
        String[] str = {"org.jetbrains.kotlin:kotlin-reflect:1.3.72",
                "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72",
                "org.projectlombok:lombok:1.18.12",
                "org.aspectj:aspectjweaver:1.9.6",
                "org.slf4j:slf4j-api:1.7.30",
                "ch.qos.logback:logback-classic:1.2.3",
                "org.assertj:assertj-core:3.16.1",
                "org.junit.jupiter:junit-jupiter-api:${junitVersion}",
                "org.junit.jupiter:junit-jupiter-engine:${junitVersion}",
                "org.jodd:jodd-lagarto:5.1.5",
                "p6spy:p6spy:3.9.1",
                "com.microsoft.sqlserver:sqljdbc4:4.0",
                "org.postgresql:postgresql:42.2.14",
                "com.h2database:h2:1.4.200",
                "mysql:mysql-connector-java:8.0.21",
                "org.xerial:sqlite-jdbc:3.32.3.1",
                "org.firebirdsql.jdbc:jaybird:4.0.1.java8"};

        for (String s : str) {
            System.out.println(Utils.gradle2MavenGAV(s));
        }
    }
}
