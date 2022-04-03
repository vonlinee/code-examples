package sample.spring.jpa.interceptor;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class SqlInterceptor implements StatementInspector {

    @Override
    public String inspect(String sql) {
        System.out.println("进入拦截器" + sql);
        sql += " and 1=1";
        return sql;
    }
}

