package code.sample.spring.transaction.programatically.v3.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
public class AccountServiceImpl {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	public void bookPurchase(int bookId, int userId, String userPass) {
		// This will handle the transaction and will commit only if all the statements inside doInTransaction() executes successfully. Otherwise transaction will be rolled back.
		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				try (Session session = sessionFactory.getCurrentSession()){
					//操作
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
}