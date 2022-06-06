package sample.spring.transaction.solution.declaratively.annotation;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 走CGlib代理
 */
@Service
public class AccountService {

	@Autowired
	public IAccountDao accountDao;
	
	@Transactional
	public void transferMoney(String from, String to, BigDecimal money, boolean throwException) {
		accountDao.outMoney(from, money);
		if (throwException) {
			throw new RuntimeException(String.format("transferMoney [%s]->[%s]: [%s]", from, to, money));
		}
		accountDao.inMoney(to, money);
	}

	public void transferMoney(String from, String to, BigDecimal money) {
		transferMoney(from, to, money, false);
	}
}
