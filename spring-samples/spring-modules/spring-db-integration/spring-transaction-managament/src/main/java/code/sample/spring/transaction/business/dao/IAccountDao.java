package code.sample.spring.transaction.business.dao;

public interface IAccountDao {
	public void outMoney(String out, Double money);
	public void inMoney(String in, Double money);
}
