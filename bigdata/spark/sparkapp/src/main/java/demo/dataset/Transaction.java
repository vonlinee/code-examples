package demo.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

// 交易数据类
@Data
@AllArgsConstructor
public class Transaction implements Serializable {
  private String userId;
  private double amount;
  private String pattern;
  private String timestamp;
}
