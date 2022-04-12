package sample.spring.transaction.solution.declaratively.annotation;

import java.math.BigDecimal;

public interface IAccountService {
	void transferMoney(String from, String to, BigDecimal money);

	void transferMoney(String from, String to, BigDecimal money, boolean throwException);
}
