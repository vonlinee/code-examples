package demo.rdd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

// 用户信息类
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
  private String userName;
  private int age;
  private String city;
  private String level;
}
