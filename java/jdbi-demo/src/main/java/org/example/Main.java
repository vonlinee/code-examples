package org.example;

import com.alibaba.druid.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.internal.HibernateSchemaManagementTool;
import org.hibernate.tool.schema.internal.exec.JdbcContext;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.HandleConsumer;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultProducer;
import org.jdbi.v3.core.statement.Batch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.Update;
import org.jooq.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

/**
 * @see org.jdbi.v3.core.mapper.RowViewMapper
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Jdbi jdbi = Jdbi.create("");

        jdbi.useTransaction(new HandleConsumer<Exception>() {
            @Override
            public void useHandle(Handle handle) throws Exception {

            }
        });

        Query query1 = jdbi.withHandle(handle -> {
            Query query = handle.createQuery("select * from t_user");

            handle.useTransaction(new HandleConsumer<Exception>() {
                @Override
                public void useHandle(Handle handle) throws Exception {

                }
            });

            query.bind("name", "zs");

            List<User> list = query.mapToBean(User.class)
                    .collectIntoList();

            Update update = handle.createUpdate("");

            Batch batch = handle.createBatch();

            batch.add("");

            int execute = update.execute();
            return query;
        });


        query1.execute(new ResultProducer<Object>() {
            @Override
            public Object produce(Supplier<PreparedStatement> statementSupplier, StatementContext ctx) throws SQLException {
                return null;
            }
        });

        JdbcClient client = JdbcClient.create(new JdbcTemplate());

        StringUtils.checkValNull()

        JdbcContext context = new JdbcContext() {
            @Override
            public JdbcConnectionAccess getJdbcConnectionAccess() {
                return null;
            }

            @Override
            public Dialect getDialect() {
                return null;
            }

            @Override
            public SqlStatementLogger getSqlStatementLogger() {
                return null;
            }

            @Override
            public SqlExceptionHelper getSqlExceptionHelper() {
                return null;
            }

            @Override
            public ServiceRegistry getServiceRegistry() {
                return null;
            }
        };



    }
}