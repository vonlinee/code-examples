package code.sample.springcloud.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class Payment implements Serializable {
    private Long id;
    private String serial;

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
}
