package code.sample.spring.transaction.programatically.v2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import code.sample.spring.transaction.business.dao.IAccountDao;

@Repository
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
			// commit the transaction
			transactionManager.commit(status);
		} catch (Exception e) {
			// rollback the transaction
			transactionManager.rollback(status);
			System.out.println("TRANSACTION FAILED");
			e.printStackTrace();
		}
	}
}
