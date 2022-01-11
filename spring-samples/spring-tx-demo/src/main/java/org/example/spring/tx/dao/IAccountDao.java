package org.example.spring.tx.dao;

public interface IAccountDao {
	public void outMoney(String out, Double money);
	public void inMoney(String in, Double money);
}
