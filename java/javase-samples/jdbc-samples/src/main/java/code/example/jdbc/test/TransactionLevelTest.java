package code.example.jdbc.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import code.example.jdbc.entity.User;
import code.example.jdbc.utils.JdbcUtils;

/**
 * 
 * @since created on 2022年7月25日
 */
public class TransactionLevelTest {

	public static void main(String[] args) {
		//test1();
		test2();
	}
	
	private static volatile boolean flag = false;
	
	public static void test1() {
		
	}
	
	public static void printIsolationLevel(Connection conn) {
		QueryRunner runner = new QueryRunner();
		String sql = "select @@tx_isolation as tx_isolation";
		try {
			List<Map<String,Object>> results = runner.execute(JdbcUtils.getLocalMySQLConnection("db_mysql"), sql, new MapHandler());
			System.out.println("[tx_isolation] => " + results.get(0).get("tx_isolation"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static BeanHandler<User> handler = new BeanHandler<>(User.class);
	
	public static void test2() {
		QueryRunner runner = new QueryRunner();
		String selectSql = "select * from t_user where id = 1 lock in share mode";
		String updateSql = "update t_user set age = age + 1 where id = 1";
		
		new Thread(() -> {
			try {
				String threadName = Thread.currentThread().getName();
				// 一个会话就是一个连接Connection
				Connection connection = JdbcUtils.getLocalMySQLConnection("db_mysql");
				// 保证两条sql在同一个事务中
				connection.setAutoCommit(false);
				printIsolationLevel(connection);
				User user1 = runner.execute(connection, selectSql, handler).get(0);
				System.out.println(threadName + " => " + user1.getAge());
				flag = true;
				Thread.sleep(3000);
				User user2 = runner.execute(connection, selectSql, handler).get(0);
				System.out.println(threadName + " => " + user2.getAge());
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "select-thread").start();
		
		new Thread(() -> {
			try {
				while (true) {
					if (flag) {
						Connection connection = JdbcUtils.getLocalMySQLConnection("db_mysql");
						printIsolationLevel(connection);
						int affectedRows = runner.update(connection, updateSql);
						if (affectedRows > 0) {
							System.out.println(Thread.currentThread().getName() + " => update");
						}
						flag = false;
						break;
					}
					System.out.println(Thread.currentThread().getName() + " => wait to update");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}, "update-thread").start();
	}
}
