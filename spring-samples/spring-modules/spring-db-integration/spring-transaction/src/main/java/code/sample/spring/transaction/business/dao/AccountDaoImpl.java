package code.sample.spring.transaction.business.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements IAccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void updateMoneyByUserName(String userName, Double money) {
		String sql = "update account set money = ? where name = ?";
		jdbcTemplate.update(sql, money, userName);
	}
}