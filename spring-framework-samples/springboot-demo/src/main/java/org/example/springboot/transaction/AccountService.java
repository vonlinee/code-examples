//package org.example.springboot.transaction;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.TransactionCallbackWithoutResult;
//import org.springframework.transaction.support.TransactionTemplate;
//
////使用TransactionTemplate JDBC操作
////@Service
//public class AccountService {
//
//	@Autowired
//	private TransactionTemplate transactionTemplate;
//	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	
//    public void transferMoney(String out, String in, Double money) {
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(
//                		TransactionStatus transactionStatus) {
//            	jdbcTemplate.update("update account set money = "
//            			+ "money - ? where name = ?", money, out);
//                int i = 1 / 0;  //此处除0模拟转账发生异常
//                jdbcTemplate.update("update account set money = "
//                		+ "money + ? where name = ?", money, in);
//            }
//        });
//    }
//}
