package sample.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    JdbcTemplate template;

    @Override
    @Transactional
    public void transferMoney(int from, int to, int money) {
        updateMoney(from, 200, false);
        if (money == 0) {
            throw new RuntimeException("transferMoney");
        }
        updateMoney(to, 200, true);
    }

    /**
     * 单条SQL
     * @param id    用户ID
     * @param money 变更的金额数量
     * @param add   增加或减少
     */
    void updateMoney(int id, int money, boolean add) {
        int i = template.update(String.format(
                "update user_account set money = money "
                        + (add ? "+" : "-")
                        + " %d where id = %d", money, id));
        if (i > 0) {
            System.out.println((add ? "add" : "cost") + " success");
        }
    }
}
