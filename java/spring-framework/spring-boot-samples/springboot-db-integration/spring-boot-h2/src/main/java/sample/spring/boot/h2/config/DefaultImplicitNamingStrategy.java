package sample.spring.boot.h2.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitAnyDiscriminatorColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitAnyKeyColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitCollectionTableNameSource;
import org.hibernate.boot.model.naming.ImplicitDiscriminatorColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitEntityNameSource;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitIdentifierColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitIndexColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitIndexNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitMapKeyColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitPrimaryKeyJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitTenantIdColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitUniqueKeyNameSource;
import org.springframework.util.StringUtils;

//@Component
public class DefaultImplicitNamingStrategy implements ImplicitNamingStrategy {

	/**
	 * 表名
	 */
	@Override
	public Identifier determinePrimaryTableName(ImplicitEntityNameSource source) {
		String entityName = source.getEntityNaming().getEntityName();
		return new Identifier(StringUtils.uncapitalize(entityName), false);
	}

	@Override
	public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
		return new Identifier("determineJoinTableName", false);
	}

	@Override
	public Identifier determineCollectionTableName(ImplicitCollectionTableNameSource source) {
		return new Identifier("determineCollectionTableName", false);
	}

	@Override
	public Identifier determineDiscriminatorColumnName(ImplicitDiscriminatorColumnNameSource source) {
		return new Identifier("determineDiscriminatorColumnName", false);
	}

	@Override
	public Identifier determineTenantIdColumnName(ImplicitTenantIdColumnNameSource source) {
		return new Identifier("determineTenantIdColumnName", false);
	}

	@Override
	public Identifier determineIdentifierColumnName(ImplicitIdentifierColumnNameSource source) {
		return new Identifier("determineIdentifierColumnName", false);
	}

	@Override
	public Identifier determineBasicColumnName(ImplicitBasicColumnNameSource source) {
		return new Identifier("determineBasicColumnName", false);
	}

	@Override
	public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
		return new Identifier("determineJoinColumnName", false);
	}

	@Override
	public Identifier determinePrimaryKeyJoinColumnName(ImplicitPrimaryKeyJoinColumnNameSource source) {
		return new Identifier("determinePrimaryKeyJoinColumnName", false);
	}

	@Override
	public Identifier determineAnyDiscriminatorColumnName(ImplicitAnyDiscriminatorColumnNameSource source) {
		return new Identifier("determineAnyDiscriminatorColumnName", false);
	}

	@Override
	public Identifier determineAnyKeyColumnName(ImplicitAnyKeyColumnNameSource source) {
		return new Identifier("determineAnyKeyColumnName", false);
	}

	@Override
	public Identifier determineMapKeyColumnName(ImplicitMapKeyColumnNameSource source) {
		return null;
	}

	@Override
	public Identifier determineListIndexColumnName(ImplicitIndexColumnNameSource source) {
		return null;
	}

	@Override
	public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
		return null;
	}

	@Override
	public Identifier determineUniqueKeyName(ImplicitUniqueKeyNameSource source) {
		return null;
	}

	@Override
	public Identifier determineIndexName(ImplicitIndexNameSource source) {
		return null;
	}
}
