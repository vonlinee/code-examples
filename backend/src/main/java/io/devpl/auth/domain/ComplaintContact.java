package io.devpl.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 机构投诉人联系信息
 */
@Data
@TableName("complaint_contact")
public class ComplaintContact implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键，与投诉ID相同，记录插入前自行设置
    @TableId(value = "contact_id", type = IdType.INPUT)
    private long contactId;

    // 联系人姓名
    private String name;

    // 联系人号码
    private String phone;

    // 是否已通知
    private boolean notice;

    // 通知时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp noticeTime;

    // 短信签名
    private String sign;

    // 短信通知内容
    private String noticeContent;

    // 非数据库字段，接收前端参数
    @TableField(exist = false)
    private String agencyName;
}
