package sample.dynamic.datasource.config;

import org.hibernate.dialect.MySQL5Dialect;

public class CustomMySQL5Dialect extends MySQL5Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
