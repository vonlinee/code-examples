package sample.spring.transaction.business.dao;

import sample.spring.transaction.model.Account;

public interface IAccountDao {
	public void updateMoneyByUserName(String userName, Double money);

	public Account queryAccountByUserName(String userName);

	public void outMoney(String userName, Double money);

	public void inMoney(String userName, Double money);
}
