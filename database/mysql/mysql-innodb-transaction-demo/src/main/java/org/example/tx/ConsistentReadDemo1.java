package org.example.tx;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.BiConsumer;

import static org.example.tx.ConsistentReadDemo1.createDataSource;

public class ConsistentReadDemo1 {

    public static DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/mysql_learn?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    volatile boolean f1 = false;
    volatile boolean f2 = false;

    public static void main(String[] args) throws SQLException {
        ConsistentReadDemo1 consistentReadDemo1 = new ConsistentReadDemo1();

        consistentReadDemo1.test1();
    }

    public void test1() throws SQLException {
        TransactionThread txa = new TransactionThread("A") {
            @Override
            public void executeTransaction() {
                transaction((connection, db) -> {
                    try {
                        int res = db.query("SELECT COUNT(c1) FROM t1 WHERE c1 = 'xyz'", rs -> rs.next() ? rs.getInt("COUNT(c1)") : -1);
                        Thread.sleep(5000);  // 等待事务B插入数据
                        f2 = true;
                        System.out.printf("SELECT count = %s\n", res);

                        while (!f1) {

                        }

                        // 删除其他事务提交的新增的数据
                        int deleteCount = db.update("DELETE FROM t1 WHERE c1 = 'xyz';");

                        System.out.printf("DELETE count = %s\n", deleteCount);

                        res = db.query(" SELECT COUNT(c2) FROM t1 WHERE c2 ='abc';", rs -> rs.next() ? rs.getInt("COUNT(c1)") : -1);
                        System.out.printf("count = %s\n", res);
                        res = db.update("UPDATE t1 SET c2 ='cba'WHERE c2 ='abc';");
                        System.out.printf("count = %s\n", res);
                    } catch (SQLException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };

        TransactionThread txb = new TransactionThread("A") {

            @Override
            public void executeTransaction() {
                while (!f2) {

                }
                transaction((connection, db) -> {
                    try {
                        int insertCount = db.insert("INSERT INTO t1 VALUES ('1', '1', '1'), ()", rs -> rs.next() ? rs.getInt("COUNT(c1)") : -1);
                        if (insertCount > 0) {
                            f1 = true;
                        }
                    } catch (Throwable throwable) {

                    }
                });
            }
        };

        txa.start();
        txb.start();
    }
}

abstract class TransactionThread extends Thread {

    static final QueryRunner db = new QueryRunner(createDataSource());
    private final String name;

    public TransactionThread(String name) {
        this.name = name;
        setName(name);
    }

    @Override
    public void run() {
        this.executeTransaction();
    }

    public abstract void executeTransaction();

    public final void transaction(BiConsumer<Connection, QueryRunner> consumer) {
        try (Connection connection = db.getDataSource().getConnection()) {
            connection.setAutoCommit(true);
            try {
                consumer.accept(connection, db);
            } catch (Throwable throwable) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}