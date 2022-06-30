package sample.redis.jedis;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Model {

    private int age;
    private String name;
    private Date bornDate;
    private String address;

}
