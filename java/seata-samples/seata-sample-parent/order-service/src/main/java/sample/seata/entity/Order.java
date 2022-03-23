package sample.seata.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "tab_order")
@Data
@Accessors(chain = true)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    private int count;

    private BigDecimal money;

    private int status;
}

