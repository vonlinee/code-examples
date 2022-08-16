package io.devpl.commons.bootstrap;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(DataSourceProperties.class)
public class BootstrapConfiguration {

}
