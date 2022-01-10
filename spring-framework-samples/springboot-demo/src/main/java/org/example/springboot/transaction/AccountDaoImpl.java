//package org.example.springboot.transaction;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.TransactionCallback;
//import org.springframework.transaction.support.TransactionTemplate;
//
////使用TransactionTemplate  Hibernate操作
////@Repository
//public class AccountDaoImpl {
//
//	@Autowired
//	private TransactionTemplate transactionTemplate;
//	
//    //注入基于第一个数据源生成的会话工厂
//    @Autowired
//    @Qualifier("sessionFactory")
//    private  SessionFactory sessionFactory;
//	
//	public void transferMoney(int from, int to, Double money) {
//		//所有doInTransaction()中的语句执行成功之后才会提交
//		//org.springframework.transaction.support.TransactionCallback<T>
//		transactionTemplate.execute(new TransactionCallback<Object>() {
//			@Override
//			public Object doInTransaction(TransactionStatus status) {
//				try {
//					//原文链接：https://blog.csdn.net/qq_15329947/article/details/85232287
//					Session session = sessionFactory.getCurrentSession();
//					Account from = session.load(Account.class, 1);
//					Account to = session.load(Account.class, 2);
//					from.setMoney(from.getMoney() - money);
//					to.setMoney(to.getMoney() + money);
//					session.update(from);
//					session.update(to);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return null;
//			}
//		});
//		
//		//org.springframework.transaction.support.TransactionCallbackWithoutResult
////		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
////			@Override
////			protected void doInTransactionWithoutResult(TransactionStatus status) {
////				
////			}
////		});
//	}
//}