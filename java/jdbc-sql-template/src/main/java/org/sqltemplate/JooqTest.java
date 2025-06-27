package org.sqltemplate;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.HandleConsumer;
import org.jdbi.v3.core.Jdbi;
import org.jooq.CloseableDSLContext;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Parser;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.impl.DSL;

import java.sql.Connection;

public class JooqTest {

    public static void main(String[] args) {
        CloseableDSLContext context = DSL.using("jdbc:mysql://localhost:3306/sakila?user=root&password=123456&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true");

        Configuration config = context.configuration();
        config.data("username", "root");
        config.data("password", "123456");
        Connection acquire = config.connectionProvider().acquire();

        Parser parser = context.parser();

        ResultQuery<Record> query = context.resultQuery("select * from film");

        Result<Record> result = query.fetch();

        query.execute();

        query.fetchResultSet();

        for (Field<?> field : result.fields()) {
            System.out.println(field);
        }

        Jdbi jdbi = Jdbi.create("");

        jdbi.useTransaction(new HandleConsumer<Exception>() {
            @Override
            public void useHandle(Handle handle) throws Exception {


            }
        });


        System.out.println(result.get(0));


        int size = result.size();

        System.out.println(size);

        System.out.println(acquire);
    }
}
