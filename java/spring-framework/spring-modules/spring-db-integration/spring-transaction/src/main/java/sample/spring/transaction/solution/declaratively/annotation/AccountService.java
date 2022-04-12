package sample.spring.transaction.solution.declaratively.annotation;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	@Autowired
	public IAccountDao accountDao;
	
	public void transferMoney(String from, String to, BigDecimal money, boolean throwException) {
		accountDao.outMoney(from, money);
		if (throwException) {
			throw new RuntimeException(String.format("transferMoney [%s]->[%to]: [%s]", from, to, money));
		}
		accountDao.inMoney(to, money);
	}

	public void transferMoney(String from, String to, BigDecimal money) {
		transferMoney(from, to, money, false);
	}
}
