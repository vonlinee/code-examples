package sample.spring.transaction.solution.declaratively.annotation;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IAccountServiceImpl implements IAccountService {

	@Autowired
	public IAccountDao accountDao;

	@Override
	@Transactional
	public void transferMoney(String from, String to, BigDecimal money, boolean throwException) {
		accountDao.outMoney(from, money);
		if (throwException) {
			throw new RuntimeException(String.format("transferMoney [%s]->[%s]: [%s]", from, to, money));
		}
		accountDao.inMoney(to, money);
	}

	@Override
	public void transferMoney(String from, String to, BigDecimal money) {
		transferMoney(from, to, money, false);
	}

	@Override
	public void transferMoney(String from, String to, double money) {
		transferMoney(from, to, BigDecimal.valueOf(money), false);
	}
}
