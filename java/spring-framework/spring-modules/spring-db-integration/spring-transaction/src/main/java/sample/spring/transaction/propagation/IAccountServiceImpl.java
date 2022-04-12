package sample.spring.transaction.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import sample.spring.transaction.utils.SpringUtils;

import java.math.BigDecimal;

@Service
public class IAccountServiceImpl implements IAccountService {

    @Autowired
    public IAccountDao accountDao;

    @Autowired
    public IAccountService accountService;  //防止事务失效情况

    @Override
    @Transactional(isolation = Isolation.DEFAULT)
    public void transferMoney(String from, String to, BigDecimal money, boolean throwException) {
        transferMoney(from, to, money.doubleValue(), throwException);
    }

    /**
     * 有事务的方法调没有事务的方法
     * @param from
     * @param to
     * @param money
     * @param throwException
     */
    public void transferMoney(String from, String to, double money, boolean throwException) {
        BigDecimal money1 = BigDecimal.valueOf(money);
        accountDao.outMoney(from, money1);
        if (throwException) {
            throw new RuntimeException(String.format("transferMoney [%s]->[%s]: [%s]", from, to, money));
        }
        accountDao.inMoney(to, money1);
    }

    @Override
    @Transactional
    public void batchTransferMoney(String from, String to, double money, int num) {
        for (int i = 0; i < num; i++) {
            // 倒数第2次次抛异常
            IAccountServiceImpl accountService = SpringUtils.getBean(IAccountServiceImpl.class);
            accountService.transferMoney(from, to, BigDecimal.valueOf(money), i == num - 2);
        }
    }
}
