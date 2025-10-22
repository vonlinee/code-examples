package demo.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserAction implements Serializable {
  private String userId;
  private String action;
  private String region;
}
