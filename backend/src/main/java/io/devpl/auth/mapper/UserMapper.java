package io.devpl.auth.mapper;

import io.devpl.auth.domain.User;
import io.devpl.auth.domain.UserType;
import io.devpl.auth.domain.UserListParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户管理Mapper
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return User
     */
    User findUserByUsername(@Param("username") String username);

    /**
     * 根据手机号查找用户
     * @param phone 手机号
     * @return User or null
     */
    User findUserByPhone(@Param("phone") String phone);

    /**
     * 统计column列，值为value的记录有多少行
     * @param column 列名
     * @param value  统计值
     * @return 记录数
     */
    int count(@Param("column") String column, @Param("value") String value);

    /**
     * 新增用户
     * @param user 用户
     * @return 影响的行数
     */
    int insert(User user);

    /**
     * 更新用户头像
     * @param userId     用户ID
     * @param avatarPath 头像路径
     * @return 影响的行数
     */
    int updateAvatar(@Param("userId") String userId, @Param("avatarPath") String avatarPath);

    /**
     * 更改绑定的手机号
     * @param userId         用户ID
     * @param newPhoneNumber 新手机号
     * @return 影响的行数
     */
    int updatePhoneNumber(@Param("userId") String userId, @Param("phone") String newPhoneNumber);

    /**
     * 更改密码
     * @param userId   用户ID
     * @param password 新密码
     * @return 影响的行数
     */
    int updatePassword(@Param("userId") String userId, @Param("password") String password);

    /**
     * 更新用户关联的机构
     * @param userId     用户ID
     * @param agencyId   机构ID
     * @param agencyName 机构名称
     * @return 影响的行数
     */
    int updateAgency(@Param("userId") String userId, @Param("agencyId") String agencyId, @Param("agencyName") String agencyName);

    /**
     * 批量保存用户，userId（主键）或者username（UNIQUE）存在时进行更新。主要用于保存基础平台用户
     * 操作字段：userId、username、password（NOT NULL）、short_name、avatar_path、user_type
     * @param userList 用户列表
     * @return 影响的行数，可能为NULL
     */
    Integer saveOrUpdateUser(@Param("list") List<User> userList);

    /**
     * 根据用户类型查找用户
     * @param userType 用户类型
     * @param verified true注册审核通过的机构用户，机构ID和名称不为null
     * @return 用户列表
     */
    List<User> listUserByUserType(@Param("userType") UserType userType, @Param("verified") boolean verified);

    /**
     * 根据条件查询用户
     * @param param 查询料件
     * @return 用户列表
     */
    List<User> listUserByCondition(UserListParam param);

    /**
     * 根据用户ID获取用户信息
     * @param userIds 用户ID列表
     * @return 用户列表
     */
    List<User> listUserByIds(@Param("list") List<String> userIds);
}
