package sample.spring.transaction.programatically.platform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import lombok.Getter;
import lombok.Setter;

@Repository
@Getter
@Setter
public class AccountDaoImpl extends JdbcDaoSupport {

	private PlatformTransactionManager transactionManager;

	public void transferMoney1(String idFrom, String idTo, float money) {
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

	public void transferMoney2(String idFrom, String idTo, float money) {
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
}
