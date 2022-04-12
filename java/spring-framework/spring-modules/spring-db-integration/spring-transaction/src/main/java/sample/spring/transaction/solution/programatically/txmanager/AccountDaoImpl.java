package sample.spring.transaction.solution.programatically.txmanager;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class AccountDaoImpl extends JdbcDaoSupport {

	@Autowired
	private PlatformTransactionManager transactionManager;

	public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
		setJdbcTemplate(jdbcTemplate);
	}

	public void transferMoney1(String idFrom, String idTo, float money) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		// 事务隔离级别-采用默认
		TransactionDefinition def = new DefaultTransactionDefinition();
		// 获取事务状态
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			// 一条sqlj就是一个事务
			jdbcTemplate.execute("update t_account set money = money - " + money + " where id = " + idFrom);
			// 这里上面的事务已经提交，不能再提交
			// Transaction is already completed - do not call commit or rollback more than
			// once per transaction
			int i = 1 / 0;
			jdbcTemplate.execute("update t_account set money = money + " + money + " where id = " + idTo);
			// 这里也不需要手动提交 transactionManager.commit(status);
		} catch (Exception e) {
			// transactionManager.rollback(status);
			e.printStackTrace();
		}
	}

	public void transferMoney2(String idFrom, String idTo, float money) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		// 事务隔离级别-采用默认
		TransactionDefinition def = new DefaultTransactionDefinition();
		// TransactionAttribute extends TransactionDefinition
		TransactionAttribute transactionAttribute = new DefaultTransactionAttribute();

		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			jdbcTemplate.execute("update t_account set money = money - " + money + " where id = " + idFrom);
			int i = 1 / 0;
			jdbcTemplate.execute("update t_account set money = money + " + money + " where id = " + idTo);
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
		}
	}

	public void transferMoney3(String idFrom, String idTo, float money) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		// 事务隔离级别-采用默认
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			jdbcTemplate.execute("update t_account set money = money - " + money + " where id = " + idFrom);
			int i = 1 / 0;
			jdbcTemplate.execute("update t_account set money = money + " + money + " where id = " + idTo);
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
		}
	}

	/**
	 * JdbcTemplate手动提交事务
	 * @param idFrom
	 * @param idTo
	 * @param money void
	 */
	public void transferMoney4(String idFrom, String idTo, float money) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Connection connection = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			jdbcTemplate.execute("update t_account set money = money - " + money + " where id = " + idFrom);
			int i = 1 / 0;
			jdbcTemplate.execute("update t_account set money = money + " + money + " where id = " + idTo);
			connection.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
