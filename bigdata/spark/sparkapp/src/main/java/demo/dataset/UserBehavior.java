package demo.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户行为数据类
 */
@Data
@AllArgsConstructor
public class UserBehavior implements Serializable {
  private String userId;
  private String action;
  private String productId;
  private String categoryId;
  private String region;
  private String timestamp;
}
