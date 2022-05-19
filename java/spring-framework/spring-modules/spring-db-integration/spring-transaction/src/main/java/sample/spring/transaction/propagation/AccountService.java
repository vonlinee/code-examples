package sample.spring.transaction.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sample.spring.transaction.utils.SpringUtils;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    public IAccountDao accountDao;

    @Autowired
    public IAccountService accountService;  //防止事务失效情况

    @Transactional(propagation = Propagation.REQUIRED)
    public void transferMoney(String from, String to, BigDecimal money, boolean throwException) {
        transferMoney(from, to, money.doubleValue(), throwException);
    }

    /**
     * 有事务的方法调没有事务的方法
     *
     * @param from
     * @param to
     * @param money
     * @param throwException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transferMoney(String from, String to, double money, boolean throwException) {
        BigDecimal money1 = BigDecimal.valueOf(money);
        accountDao.outMoney(from, money1);
        if (throwException) {
            throw new RuntimeException(String.format("transferMoney [%s]->[%s]: [%s]", from, to, money));
        }
        accountDao.inMoney(to, money1);
    }

    @Transactional
    public void batchTransferMoney(String from, String to, double money, int num) {
        num = num > 3 ? num : 5;
        for (int i = 0; i < num; i++) {
            // 倒数第2次次抛异常
            IAccountService accountService = SpringUtils.getBean(IAccountService.class);
            accountService.transferMoney(from, to, BigDecimal.valueOf(money), i == num - 2);
        }
    }

    @Transactional
    public void batchTransferMoney1(String from, String to, double money, int num) {
        num = num > 3 ? num : 5;
        for (int i = 0; i < num; i++) {
            // 倒数第2次次抛异常
            // IAccountService accountService = SpringUtils.getBean(IAccountService.class);
            transferMoney(from, to, money, i == num - 2);
        }
    }

    public void batchTransferMoneyNoTransactional(String from, String to, double money, int num) {
        num = num > 3 ? num : 5;
        for (int i = 0; i < num; i++) {
            // 倒数第2次次抛异常
            // IAccountService accountService = SpringUtils.getBean(IAccountService.class);
            transferMoney(from, to, money, i == num - 2);
        }
    }
}
