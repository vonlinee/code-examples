package sample.spring.transaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sample.spring.transaction.bean.Flow;
import sample.spring.transaction.dao.impl.OracleDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OracleDaoImpl implements OracleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 更多JDBC 的使用可以参考官方文档
     * @see <a href="https://docs.spring.io/spring/docs/5.1.3.RELEASE/spring-framework-reference/data-access.html#jdbc-JdbcTemplate">JdbcTemplate</a>
     */
    public List<Flow> get() {
        List<Flow> flows = jdbcTemplate.query("select * from APEX_030200.WWV_FLOW_CALS where ID = ? ", new Object[]{217584603977429772L},
                new RowMapper<Flow>() {
                    public Flow mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Flow flow = new Flow();
                        flow.setId(rs.getLong("ID"));
                        flow.setFlowId(rs.getLong("FLOW_ID"));
                        flow.setPlugId(rs.getLong("PLUG_ID"));
                        return flow;
                    }

                });
        return flows;
    }
}
