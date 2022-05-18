package sample.spring.transaction.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class AccountDaoNoImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void outMoney(String userName, BigDecimal money) {
		jdbcTemplate.update("update t_account set money = money - ? where name = ?", money.doubleValue(), userName);
	}

	public void inMoney(String userName, BigDecimal money) {
		jdbcTemplate.update("update t_account set money = money + ? where name = ?", money.doubleValue(), userName);
	}
}
