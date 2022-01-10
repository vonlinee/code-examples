//package org.example.springboot.transaction;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//
////使用PlatformTransactionManager操作
////@Service
//public class AccountService2 {
//	@Autowired
//	TransactionDefinition td;
//	@Autowired
//	PlatformTransactionManager ptm;
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//
//	public void transferMoney(String out, String in, Double money) {
//    	TransactionStatus status = ptm.getTransaction(td); // 获得事务状态
//    	try {
//			jdbcTemplate.update("update account set money = "
//					+ "money - ? where name = ?", money, out);
////		    int i = 1 / 0;  //此处除0模拟转账发生异常
//		    jdbcTemplate.update("update account set money = "
//		    		+ "money + ? where name = ?", money, in);
//    		ptm.commit(status);// 提交
//    	} catch (Exception e) {
//    		ptm.rollback(status);// 回滚
//    	}
//    }
//}
