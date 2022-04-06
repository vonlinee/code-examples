package sample.sharding.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sample.sharding.jdbc.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 编写单元测试
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJdbcApplication.class})
public class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testInsertOrder() {
        for (int i = 0; i < 10; i++) {
            orderMapper.insertOrder(new BigDecimal((i + 1) * 5), 1L, "WAIT_PAY");
        }
    }

    @Test
    public void testSelectOrderbyIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(373771636085620736L);
        ids.add(373771635804602369L);
        List<Map<String, Object>> maps = orderMapper.selectOrderbyIds(ids);
        System.out.println(maps);
    }
}

