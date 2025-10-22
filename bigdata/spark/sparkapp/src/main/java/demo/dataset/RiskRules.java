package demo.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

// 风险规则类
@Data
@AllArgsConstructor
public class RiskRules implements Serializable {
  private double maxAmountPerTransaction;
  private Set<String> suspiciousPatterns;
  private int highFrequencyThreshold;
}
