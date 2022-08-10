package io.maker.codegen.mbp.sql;

public abstract class SqlStatement {

    public abstract String getSql();

    public interface Builder<T extends SqlStatement> {
        SqlStatement build();
    }

    public static SelectSqlStamtent.Builder select() {
        return new SelectSqlStamtent.Builder();
    }
}
