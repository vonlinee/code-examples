package io.devpl.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class SmsTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "code", type = IdType.INPUT)
    private String code;

    private String content;

    private Timestamp createTime;

    private Timestamp updateTime;
}
