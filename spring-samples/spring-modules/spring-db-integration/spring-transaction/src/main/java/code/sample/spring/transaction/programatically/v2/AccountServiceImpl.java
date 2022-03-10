package code.sample.spring.transaction.programatically.v2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import code.sample.spring.transaction.business.dao.IAccountDao;

public class AccountServiceImpl {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IAccountDao accountDao;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	public void transferMoney() {
		// 获取事务操作类
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try (Session session = sessionFactory.getCurrentSession()) {
			//操作
			transfer("zs", "ls", 200D);
			transactionManager.commit(status); //提交事务
		} catch (Exception e) {
			// rollback the transaction
			transactionManager.rollback(status);
			System.out.println("TRANSACTION FAILED");
			e.printStackTrace();
		}
	}
	
    private void transfer(String out, String in, Double money) {
        accountDao.outMoney(out, money);
        int i = 1 / 0;  //此处除0模拟转账发生异常
        accountDao.inMoney(in, money);
    }
}
