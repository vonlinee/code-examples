package io.spring.boot.common;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import io.spring.boot.common.db.DataSourceConfiuration;

@Configuration
@AutoConfigureAfter(DataSourceConfiuration.class)
public class SpringDataJPAConfiguration {

}
