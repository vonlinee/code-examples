package io.devpl.auth.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户信息
 */
@Entity
@Table(name = "user", schema = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 唯一编码
     */
    @Column(name = "USER_CODE")
    private String userCode;

    /**
     * 用户名，登录账号
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 密码  // @JsonIgnore  // 使用JsonIgnore的话，接收参数时接收不到
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 快捷用户名，账号名称，一般为中文名
     */
    @Column(name = "SHORT_NAME")
    private String shortName;

    /**
     * 性别
     */
    @Column(name = "GENDER")
    private String gender;

    /**
     * 电话号码
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 头像路径
     */
    @Column(name = "HEAD_PORTRAIT")
    private String headPortrait;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    /**
     * 上次更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Timestamp updateTime;

    /**
     * 用户状态
     */
    @Column(name = "STATUS")
    private int status;

    /**
     * 逻辑删除字段
     */
    @Column(name = "IS_ENABLE")
    private boolean isEnable;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
