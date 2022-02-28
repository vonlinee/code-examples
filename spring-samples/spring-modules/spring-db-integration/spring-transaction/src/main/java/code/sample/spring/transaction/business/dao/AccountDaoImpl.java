package code.sample.spring.transaction.business.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import code.sample.spring.transaction.model.Account;

@Repository
public class AccountDaoImpl implements IAccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void updateMoneyByUserName(String userName, Double money) {
		String sql = "update account set money = ? where name = ?";
		jdbcTemplate.update(sql, money, userName);
	}

	@Override
	public Account queryAccountByUserName(String userName) {
		String sql = "select * from account where name = ?";
		return jdbcTemplate.queryForObject(sql, Account.class, userName);
	}

	@Override
	public void outMoney(String userName, Double money) {
		String sql = "update account set money = money - ? where name = ?";
		jdbcTemplate.update(sql, money, userName);
	}

	@Override
	public void inMoney(String userName, Double money) {
		String sql = "update account set money = money + ? where name = ?";
		jdbcTemplate.update(sql, money, userName);
	}
}