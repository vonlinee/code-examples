package sample.spring.transaction.problem;

import java.math.BigDecimal;

public interface IAccountDao {
	void outMoney(String userName, BigDecimal money);
	void inMoney(String userName, BigDecimal money);
}