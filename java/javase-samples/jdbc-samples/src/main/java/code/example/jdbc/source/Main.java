package code.example.jdbc.source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import code.example.jdbc.utils.JdbcUtils;

public class Main {
	
	public static void main(String[] args) throws SQLException {
		//com.mysql.jdbc.JDBC4Connection
		Connection connection = JdbcUtils.getConnection("db_mysql");
		connection.setAutoCommit(false);
		String from = "update t_account set money = money - ? where id = ?";
		String to = "update t_account set money = money + ? where id = ?";
		try {
			transferMoney(connection, from, "1", 200.0f);
			connection.commit();
			int i = 1 / 0;
			transferMoney(connection, to, "2", 200.0f);
			connection.commit();
		} catch (Exception e) {
			System.out.println("rollback");
			connection.rollback();
		} finally {
			DbUtils.closeQuietly(connection);
		}
	}
	
	public static void transferMoney(Connection conn, String sql, String id, float money) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, money);
			pstmt.setString(2, id);
			boolean result = pstmt.execute();
			if (result) {
				System.out.println("execute successfully!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}
}
