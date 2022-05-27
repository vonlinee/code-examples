package sample.spring.boot.h2.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Identifier会在不同方法内部进行传递
 */
public class DynamicNamingStrategy implements PhysicalNamingStrategy {
	
	private PhysicalNamingStrategy delegate;
	
	private DynamicNamingStrategy() {}
	
	public DynamicNamingStrategy(PhysicalNamingStrategy delegate) {
		if (delegate == null) {
			delegate = new DynamicNamingStrategy();
		}
		this.delegate = delegate;
	}
	
	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return delegate.toPhysicalCatalogName(name, jdbcEnvironment);
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return delegate.toPhysicalSchemaName(name, jdbcEnvironment);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return delegate.toPhysicalTableName(name, jdbcEnvironment);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return delegate.toPhysicalSequenceName(name, jdbcEnvironment);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return delegate.toPhysicalColumnName(name, jdbcEnvironment);
	}
}