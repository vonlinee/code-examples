package io.devpl.spring.data.jpa;

import org.hibernate.boot.model.naming.*;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * 自定义命名策略
 * https://www.cnblogs.com/CreateMyself/p/12373664.html
 */
public class HibernateNamingStrategy implements PhysicalNamingStrategy, ImplicitNamingStrategy {

    private ImplicitNamingStrategy delegate = new ImplicitNamingStrategyLegacyJpaImpl();

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    // ================================ 隐式命名策略 ImplicitNamingStrategy ===============================

    @Override
    public Identifier determinePrimaryTableName(ImplicitEntityNameSource source) {
        return delegate.determinePrimaryTableName(source);
    }

    @Override
    public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
        return delegate.determineJoinTableName(source);
    }

    @Override
    public Identifier determineCollectionTableName(ImplicitCollectionTableNameSource source) {
        return delegate.determineCollectionTableName(source);
    }

    @Override
    public Identifier determineDiscriminatorColumnName(ImplicitDiscriminatorColumnNameSource source) {
        return delegate.determineDiscriminatorColumnName(source);
    }

    @Override
    public Identifier determineTenantIdColumnName(ImplicitTenantIdColumnNameSource source) {
        return delegate.determineTenantIdColumnName(source);
    }

    @Override
    public Identifier determineIdentifierColumnName(ImplicitIdentifierColumnNameSource source) {
        return delegate.determineIdentifierColumnName(source);
    }

    @Override
    public Identifier determineBasicColumnName(ImplicitBasicColumnNameSource source) {
        return delegate.determineBasicColumnName(source);
    }

    @Override
    public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
        return delegate.determineJoinColumnName(source);
    }

    @Override
    public Identifier determinePrimaryKeyJoinColumnName(ImplicitPrimaryKeyJoinColumnNameSource source) {
        return delegate.determinePrimaryKeyJoinColumnName(source);
    }

    @Override
    public Identifier determineAnyDiscriminatorColumnName(ImplicitAnyDiscriminatorColumnNameSource source) {
        return delegate.determineAnyDiscriminatorColumnName(source);
    }

    @Override
    public Identifier determineAnyKeyColumnName(ImplicitAnyKeyColumnNameSource source) {
        return delegate.determineAnyKeyColumnName(source);
    }

    @Override
    public Identifier determineMapKeyColumnName(ImplicitMapKeyColumnNameSource source) {
        return delegate.determineMapKeyColumnName(source);
    }

    @Override
    public Identifier determineListIndexColumnName(ImplicitIndexColumnNameSource source) {
        return delegate.determineListIndexColumnName(source);
    }

    @Override
    public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
        return delegate.determineForeignKeyName(source);
    }

    @Override
    public Identifier determineUniqueKeyName(ImplicitUniqueKeyNameSource source) {
        return delegate.determineUniqueKeyName(source);
    }

    @Override
    public Identifier determineIndexName(ImplicitIndexNameSource source) {
        return delegate.determineIndexName(source);
    }
}
