package org.example.springboot.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService3 {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void transferMoney(String out, String in, Double money, boolean flag) {
		String sql = "update account set money = money %s ? where name = ?";
		jdbcTemplate.update(String.format(sql, "-"), money, out);
		if (flag) {
			int i = 1 / 0;
		}
		jdbcTemplate.update(String.format(sql, "+"), money, in);
	}
}
