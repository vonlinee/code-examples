package org.example.spring.tx.service;

import org.example.spring.tx.dao.IAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class AccountServiceImpl {
    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public void transferMoney(String out, String in, Double money) {
    	transfer(out, in, money);
    }
    
    @Transactional
    public void transfer(String out, String in, Double money) {
        accountDao.outMoney(out, money);
        int i = 1 / 0;  //此处除0模拟转账发生异常
        accountDao.inMoney(in, money);
    }
    
//    public void transfer1(String out, String in, Double money) {
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(
//                			TransactionStatus transactionStatus) {
//                accountDao.outMoney(out, money);
//                int i = 1 / 0;  //此处除0模拟转账发生异常
//                accountDao.inMoney(in, money);
//            }
//        });
//    }
}