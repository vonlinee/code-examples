package sample.spring.transaction.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sample.spring.transaction.bean.Relation;

import java.util.List;

/**
 * 更多JDBC 的使用可以参考官方文档
 * @see <a href="https://docs.spring.io/spring/docs/5.1.3.RELEASE/spring-framework-reference/data-access.html#jdbc-JdbcTemplate">JdbcTemplate</a>
 */
@Service(value = "template")
public class TemplateService implements IService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String transfer() {
        jdbcTemplate.query("select * from help_keyword where help_keyword_id = ? ", new Object[]{691},
                (rs, rowNum) -> {
                    Relation relation = new Relation();
                    relation.setId(rs.getString("help_keyword_id"));
                    relation.setName(rs.getString("name"));
                    return relation;
                });
        return null;
    }
}
