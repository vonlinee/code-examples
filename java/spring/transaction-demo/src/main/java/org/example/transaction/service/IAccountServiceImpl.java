package org.example.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;

@Service
public class IAccountServiceImpl implements IAccountService {

    @Autowired
    JdbcTemplate template;
    @Autowired
    ApplicationContext context;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transferMoney(String from, String to, float money) {
        // 转出账户扣钱
        template.update("update account set money = money - ? where name = ?", money, from);

        Connection connection =
                template.getDataSource().getConnection();

        // @Autowired ApplicationContext context;
        // context.getBean(IAccountService.class).transferMoney(from, to, money);
        if (money == 200) {
            throw new RuntimeException("throw exception intentionally!");
        }
        // 转入账户加钱
        template.update("update account set money = money + ? where name = ?", money, to);
    }

}
