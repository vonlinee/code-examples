package sample.spring.transaction.business.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sample.spring.transaction.entity.Account;

@Repository
public class AccountDaoImpl implements IAccountDao, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(AccountDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void updateMoneyByUserName(String userName, Double money) {
        String sql = "update t_account set money = ? where name = ?";
        LOG.info("\nExecute SQL => " + String.format(sql.replace("?", "%s"), money, userName));
        jdbcTemplate.update(sql, money, userName);
    }

    @Override
    public Account queryAccountByUserName(String userName) {
        String sql = "select * from t_account where name = ?";
        LOG.info("\nExecute SQL => " + String.format(sql.replace("?", "%s"), userName));
        return jdbcTemplate.queryForObject(sql, Account.class, userName);
    }

    @Override
    public void outMoney(String userName, Double money) {
        String sql = "update t_t_account set money = money - ? where name = ?";
        LOG.info("\nExecute SQL => " + String.format(sql.replace("?", "%s"), money, userName));
        jdbcTemplate.update(sql, money, userName);
    }

    @Override
    public void inMoney(String userName, Double money) {
        String sql = "update t_account set money = money + ? where name = ?";
        LOG.info("\nExecute SQL => " + String.format(sql.replace("?", "%s"), money, userName));
        jdbcTemplate.update(sql, money, userName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}