package io.devpl.auth.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 局端基础平台用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseUser implements Serializable {

    // 学号/工号/编号
    @JsonAlias("UserID")
    private String userId;

    // 用户名，对应本系统User的username
    @JsonAlias("ShortName")
    private String shortName;

    // 姓名，对应本系统User的shortName
    @JsonAlias("UserName")
    private String userName;

    // 性别
    @JsonAlias("Gender")
    private String gender;

    // 用户类型：0—学校管理员；1—教师；2—学生；3—家长；6—平台管理员，9—监事员
    @JsonAlias("UserType")
    private Integer userType;

    // 用户级别，在用户类型上进一步细分：0学校管理员（1—普通管理员，2—超级管理员）；1教师（100000 等，后 5 位分别代表：任
    // 课教师、班主任、教研者、学科主管、校领导）；2学生（0—普通学生，1—班长）；3家长（0）；6平台管理员（1—普通
    // 管理员；2—超级管理员）；9监事员（0）
    @JsonAlias("UserClass")
    private Integer userClass;

    // 档案证件照片 绝对路径
    @JsonAlias("PhotoPath")
    private String photoPath;

    // 个性签名
    @JsonAlias("Sign")
    private String sign;

    // 账号用户头像 绝对路径
    @JsonAlias("AvatarPath")
    private String avatarPath;

    // 上次登录时间
    @JsonAlias("PreLoginTime")
    private String preLoginTime;

    // 上次登录IP
    @JsonAlias("PreLoginIP")
    private String preLoginIp;

    // 记录的上一次修改时间
    @JsonAlias("UpdateTime")
    private String updateTime;

    // 登录来源信息
    @JsonAlias("LoginInfo")
    private String loginInfo;

    // 点控状态
    @JsonAlias("LockerState")
    private String lockerState;


    // 学校用户相关字段，非学校用户为空
    // 学校ID
    @JsonAlias("SchoolID")
    private String schoolId;

    // 学校名称
    @JsonAlias("SchoolName")
    private String schoolName;

    // 系统内唯一ID
    @JsonAlias("InnerID")
    private String innerId;

    // 年级相关字段
    // 年级ID，学生特有属性
    @JsonAlias("GradeID")
    private String gradeId;

    // 年级名称，学生特有属性
    @JsonAlias("GradeName")
    private String gradeName;

    // 年级统一标志，中小学K1-K12，中职M1-M3，高职H1-H3，大学U1-U5
    @JsonAlias("GlobalGrade")
    private String globalGrade;

    // 班级、教师组相关字段
    // 班级/教师组ID，学生、教师特有属性
    @JsonAlias("GroupID")
    private String groupId;

    // 班级/教师组名称，学生、教师特有属性
    @JsonAlias("GroupName")
    private String groupName;


    // 教师教学字段：教育局端用户无用途
    // 所教学科ID，教师属性
    @JsonAlias("SubjectIDs")
    private String subjectIds;

    // 所教学科，教师属性
    @JsonAlias("SubjectNames")
    private String subjectNames;


    // 大学环境相关字段，教育局端用户无用途
    // 学院ID，大学环境下学生、教师、学院领导特有属性
    @JsonAlias("CollegeID")
    private String collegeId;

    // 学院名称，大学环境下学生、教师、学院领导特有属性
    @JsonAlias("CollegeName")
    private String collegeName;

    // 专业ID，大学环境特有属性
    @JsonAlias("MajorID")
    private String majorId;

    // 专业名称，大学环境特有属性
    @JsonAlias("MajorName")
    private String majorName;
}
