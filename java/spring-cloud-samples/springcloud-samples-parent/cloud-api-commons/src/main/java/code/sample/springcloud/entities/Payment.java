package code.sample.springcloud.entities;

import java.io.Serializable;

import lombok.Data;

public class Payment implements Serializable {
    private Long id;
    private String serial;

    //RestTemplate要求有默认构造方法
    public Payment() {
    }

    public Payment(Long id, String serial) {
        this.id = id;
        this.serial = serial;
    }

    public Payment(String serial) {
        this.serial = serial;
    }

    public Payment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
