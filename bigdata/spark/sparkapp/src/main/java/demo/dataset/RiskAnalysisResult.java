package demo.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

// 风险分析结果类
@Data
@AllArgsConstructor
public class RiskAnalysisResult implements Serializable {
  private String userId;
  private double amount;
  private String pattern;
  private String riskLevel;
  private String riskReasons;
}
