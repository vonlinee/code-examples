package sample.spring.transaction.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.spring.transaction.bean.Relation;

import java.util.List;

@Service(value = "transactional")
public class TransactionalService implements IService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public String transfer() {
        jdbcTemplate.execute("update t_account set money = money + 200 where id = 1");
        int i = 1 / 0;
        jdbcTemplate.execute("update t_account set money = money - 200 where id = 2");
        return "";
    }
}
