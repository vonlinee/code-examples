package io.maker.extension.mybatis.config;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

public class MappedStatementBuilder {

    public MappedStatementBuilder(Configuration configuration, String resource) {
        MapperBuilderAssistant mapperBuilder = new MapperBuilderAssistant(configuration, resource);
    }
}
