package org.sqltemplate;

import com.google.common.collect.HashBasedTable;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.HandleConsumer;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.statement.Batch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.SqlParser;
import org.jdbi.v3.core.statement.Update;
import org.jooq.CloseableDSLContext;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.gui.STViz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@MapperScan
public class Main {
    public static void main(String[] args) throws Exception {
        STGroupFile stf = new STGroupFile("D:\\Develop\\Code\\code-samples\\java\\jdbc-sql-template\\src\\test\\java\\test.stg");

        ST st = stf.getInstanceOf("selectList");
        st.add("name", "zs");
        st.add("code", "sd sd");
        st.add("list", Arrays.asList(1, 3, 4, 5));
        String result = st.render();

        STViz.test1();
        System.out.println(result);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("");
        dataSource.setDriverClassName("com");

        JdbcClient client = JdbcClient.create(dataSource);

        Jdbi jdbi = Jdbi.create(dataSource);

        jdbi.useTransaction(new HandleConsumer<Exception>() {
            @Override
            public void useHandle(Handle handle) throws Exception {

                Batch batch = handle.createBatch();

                int[] execute = batch.execute();

                Query query = handle.createQuery("");

                ConfigRegistry config = query.getConfig();

                query.mapTo(String.class);

                Update update = handle.createUpdate("");



                handle.createBatch();
            }
        });

        QueryRunner runner = new QueryRunner();
        runner.execute("", 1, new ResultSetHandler<>() {
            @Override
            public Object handle(ResultSet rs) throws SQLException {
                return null;
            }
        });

        NamedParameterJdbcOperations

    }
}