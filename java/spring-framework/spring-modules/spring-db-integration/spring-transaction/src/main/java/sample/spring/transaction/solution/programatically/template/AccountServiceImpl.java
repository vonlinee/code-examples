package sample.spring.transaction.solution.programatically.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import sample.spring.transaction.problem.IAccountDao;

@Repository
public class AccountServiceImpl {

	// extends DefaultTransactionDefinition
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private IAccountDao accountDao;
	
	public void transferMoney1(int bookId, int userId, String userPass) {
		// This will handle the transaction and will commit only if all the statements inside doInTransaction() executes successfully. Otherwise transaction will be rolled back.
		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				// 业务操作
				return null;
			}
		});
	}
	
	public void transferMoney2(int bookId, int userId, String userPass) {
		// This will handle the transaction and will commit only if all the statements inside doInTransaction() executes successfully. Otherwise transaction will be rolled back.
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
			}
		});
	}
}