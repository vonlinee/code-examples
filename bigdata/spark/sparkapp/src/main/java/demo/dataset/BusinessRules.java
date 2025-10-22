package demo.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 定义业务规则类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessRules implements Serializable {
  private Set<String> blacklistedUsers;
  private Set<String> highRiskRegions;
  private Set<String> vipUsers;
  private Map<String, String> productCategories;
}
