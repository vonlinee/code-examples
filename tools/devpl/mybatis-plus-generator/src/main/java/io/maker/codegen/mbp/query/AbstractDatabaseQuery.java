package io.maker.codegen.mbp.query;

import org.jetbrains.annotations.NotNull;

import io.maker.codegen.mbp.config.DataSourceConfig;
import io.maker.codegen.mbp.config.builder.ConfigBuilder;

public abstract class AbstractDatabaseQuery implements IDatabaseQuery {

	protected final ConfigBuilder configBuilder;

	protected final DataSourceConfig dataSourceConfig;

	public AbstractDatabaseQuery(@NotNull ConfigBuilder configBuilder) {
		this.configBuilder = configBuilder;
		this.dataSourceConfig = configBuilder.getDataSourceConfig();
	}

	@NotNull
	public ConfigBuilder getConfigBuilder() {
		return configBuilder;
	}

	@NotNull
	public DataSourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}

}
