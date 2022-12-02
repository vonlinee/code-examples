package io.devpl.auth.service;

import io.devpl.auth.domain.BaseIdentityType;
import io.devpl.auth.domain.BaseUser;
import io.devpl.auth.domain.User;

import java.io.IOException;
import java.util.List;

/**
 * 基础平台用户（局端用户）管理Service接口
 */
public interface IRemoteUserService {

    /**
     * 检查token指定用户是否在线
     * @param token 登陆后的令牌
     * @return 在线为true
     */
    boolean checkOnline(String token) throws IOException;

    /**
     * 通过token获取在线用户信息
     * @param token 登陆后的令牌
     * @return 用户信息
     */
    BaseUser findUserByToken(String token) throws IOException;

    /**
     * 退出登录
     * @param token 登陆后的令牌
     * @return 成功登出为true
     */
    boolean logout(String token) throws Exception;

    /**
     * 基础平台用户：根据登录Token获取默认身份
     * @param token 登录令牌
     * @param userId 用户ID
     * @param moduleId 可选，模块ID
     * @return BaseIdentityType 默认身份
     */
    BaseIdentityType getDefaultIdentityCodeAndImgByToken(String token, String userId, String moduleId) throws Exception;

    /**
     * 根据身份代码获取身份详情
     * @param eduId 教育局ID
     * @param token 登录令牌
     * @param identityCodes 身份代码：多个使用,分割
     * @param returnModuleIds 是否返回可访问的模块ID
     * @return 身份列表（名称、背景图等）
     */
    List<BaseIdentityType> listIdentityTypeByCode(String eduId, String token, String identityCodes, boolean returnModuleIds);

    /**
     * 条件查询教育局领导
     * @param token 令牌
     * @param userId 用户ID
     * @param userName 用户名，模糊查询
     * @param updateTime 作用未知
     * @param dataModel 作用未知
     * @return User用户列表
     */
    List<User> listEduLeaderByCondition(String token, String userId, String userName, String updateTime, String dataModel) throws Exception;

    /**
     * 条件查询管理员
     * @param token 令牌
     * @param userId 用户ID
     * @param userName 用户名，模糊查询
     * @param schoolId 学校ID
     * @param userType 用户类型
     * @param updateTime 作用未知
     * @param dataModel 作用未知
     * @return User用户列表
     */
    List<User> listAdminByCondition(String token, String userId, String userName, String schoolId, String userType, String updateTime, String dataModel) throws Exception;
}
