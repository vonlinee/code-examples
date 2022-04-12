package sample.spring.transaction.solution.declaratively.annotation;

import java.math.BigDecimal;

public interface IAccountDao {
	void outMoney(String userName, BigDecimal money);
	void inMoney(String userName, BigDecimal money);
}