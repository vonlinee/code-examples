package code.sample.spring.transaction.business.dao;

public interface IAccountDao {
	public void updateMoneyByUserName(String userName, Double money);
}
