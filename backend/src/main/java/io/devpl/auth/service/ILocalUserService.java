package io.devpl.auth.service;

import io.devpl.auth.domain.User;
import io.devpl.auth.domain.UserType;
import io.devpl.auth.domain.UserListParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 机构用户管理器
 * @author Xu Jiabao
 * @since 2022/2/8
 */
public interface ILocalUserService {

    /**
     * 新增机构用户/管理端用户
     * 设置UUID并对密码加密
     * @param user 用户
     * @return 影响的行数
     */
    int insert(User user);

    /**
     * 更新机构用户头像
     * @param user 用户
     * @param avatar 头像
     * @return 数据库影响的行数，成功1
     */
    int updateAvatar(User user, MultipartFile avatar) throws IOException;

    /**
     * 更新机构用户头像
     * @param user 用户
     * @param avatar 头像地址，改为OBS地址，格式：#桶名#ObjectKey#
     * @return 数据库影响的行数，成功1
     */
    int updateAvatar(User user, String avatar) throws IOException;

    /**
     * 更新密码，密码加密后保存
     * @param user 用户，含ID和用户名
     * @param password 新密码
     * @return 影响的行数
     */
    int updatePassword(User user, String password);

    /**
     * 更新绑定的手机号
     * @param userId 用户ID
     * @param newPhoneNumber 新手机号
     * @return 影响的行数
     */
    int updatePhoneNumber(String userId, String newPhoneNumber);

    /**
     * 更新用户关联的机构
     * @param userId 用户ID
     * @param agencyId 机构ID
     * @param agencyName 机构名称
     * @return 影响的行数
     */
    int updateAgency(String userId, int agencyId, String agencyName);

    /**
     * 批量新增用户，userId和username存在时进行更新
     * 主要用于：同步基础平台的用户到本地用户表，回显审核人姓名与头像时不从基础平台获取，减少延迟
     * @param userList 用户列表
     * @return 影响的行数
     */
    Integer saveOrUpdateUser(List<User> userList);

    /**
     * 根据用户名查找用户，用户名需要唯一，注册时验证
     * @param username 用户名
     * @return 存在true，不能存在false
     */
    boolean usernameExist(String username);

    /**
     * 根据用户名查找用户，用户名需要唯一，注册时验证
     * @param phoneNumber 手机号
     * @return 存在true，不能存在false
     */
    boolean phoneNumberExist(String phoneNumber);

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @param containPassword 是否包含密码
     * @return User
     */
    User findUserByUsername(String username, boolean containPassword);

    /**
     * 根据手机号查找用户，
     * @param phone 手机号
     * @return User 不含密码
     */
    User findUserByPhone(String phone);

    /**
     * 根据用户类型查找用户
     * @param userType 用户类别
     * @param verified true注册审核通过的用户
     * @return 用户列表
     */
    List<User> listUserByUserType(UserType userType, boolean verified);

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
    List<User> listUserByIds(List<String> userIds);
}
