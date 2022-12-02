package io.devpl.auth.service;

import io.devpl.auth.config.ConfigProperties;
import io.devpl.auth.domain.UserListParam;
import io.devpl.auth.domain.User;
import io.devpl.auth.domain.UserType;
import io.devpl.auth.mapper.UserMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 机构用户管理Service实现类
 * @author Xu Jiabao
 * @since 2022/2/8
 */
@Service
public class LocalUserServiceImpl implements ILocalUserService {

    private final UserMapper userMapper;
    private final ConfigProperties configProperties;

    public LocalUserServiceImpl(UserMapper userMapper, ConfigProperties configProperties) {
        this.userMapper = userMapper;
        this.configProperties = configProperties;
    }

    /**
     * 根据用户名查找用户
     * @param username        用户名
     * @param containPassword 是否包含密码
     * @return User
     */
    @Override
    public User findUserByUsername(String username, boolean containPassword) {
        User user = userMapper.findUserByUsername(username);
        if (user != null && !containPassword) {
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public User findUserByPhone(String phone) {
        User user = userMapper.findUserByPhone(phone);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 机构用户注册。设置UUID，加密密码
     * Controller中已做数据校验
     * @param user 用户
     * @return 影响的行数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(User user) {
        // UUID 36位（32位字符，4位-）
        user.setUserId(UUID.randomUUID().toString());
        // 默认昵称和用户名一致
        user.setShortName(user.getUsername());
        // 加密，用户名作为盐值
        String encryptedPwd = new SimpleHash(ConfigProperties.ENCRYPT_ALGORITHM, user.getPassword(),
                ByteSource.Util.bytes(user.getUsername()), ConfigProperties.HASH_ITERATIONS).toHex();
//        String encryptedPwd = new SimpleHash(ConfigProperties.ENCRYPT_ALGORITHM, user.getPassword(),
//                null, ConfigProperties.HASH_ITERATIONS).toHex();
        user.setPassword(encryptedPwd);
        // 设置用户身份为机构用户
        // user.setUserType(UserType.AGENCY);
        return userMapper.insert(user);
    }

    /**
     * 更新头像，以用户ID作为头像文件名，保存在avatarDir指定目录下，如果头像已先进行删除
     * @param user   用户
     * @param avatar 头像
     * @return 影响的行数
     */
    @Override
    public int updateAvatar(User user, MultipartFile avatar) throws IOException {
        File saveDir = new File(configProperties.getPrivateAttachmentSaveDir(), "/avatar");
        if (!saveDir.exists()) {
            saveDir.mkdirs();
            // throw new IOException("文件夹创建失败，请检查系统权限" + saveDir.getAbsolutePath());
        }
        String ext = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().lastIndexOf("."));
        File target = new File(saveDir, user.getUserId() + ext);
        // 直接覆盖
//        if (target.exists() && !target.delete()) {
//            throw new IOException("原头像删除失败，请检查系统权限" + target.getAbsolutePath());
//        }
        avatar.transferTo(target);
        // 设置头像路径，也是访问路径
        // user.setAvatarPath("/avatar/" + target.getName());
        return userMapper.updateAvatar(user.getUserId(), "/avatar/" + target.getName());
    }

    /**
     * 更新用户头像OBS地址
     * @param user   用户
     * @param avatar 头像
     * @return 影响的行数
     */
    @Override
    public int updateAvatar(User user, String avatar) throws IOException {
        return userMapper.updateAvatar(user.getUserId(), avatar);
    }

    /**
     * 更新机构用户密码，密码加密
     * @param user     用户，含ID和用户名
     * @param password 新密码
     * @return 影响的行数
     */
    @Override
    public int updatePassword(User user, String password) {
        // 用户名作为盐值
        String encryptedPwd = new SimpleHash(ConfigProperties.ENCRYPT_ALGORITHM, password,
                ByteSource.Util.bytes(user.getUsername()), ConfigProperties.HASH_ITERATIONS).toHex();
//        String encryptedPwd = new SimpleHash(ConfigProperties.ENCRYPT_ALGORITHM, password,
//                null, ConfigProperties.HASH_ITERATIONS).toHex();
        return userMapper.updatePassword(user.getUserId(), encryptedPwd);
    }

    /**
     * 更新绑定手机号
     * @param userId         用户ID
     * @param newPhoneNumber 新手机号
     * @return 影响的行数
     */
    @Override
    public int updatePhoneNumber(String userId, String newPhoneNumber) {
        return userMapper.updatePhoneNumber(userId, newPhoneNumber);
    }

    /**
     * 更新用户关联的机构
     * @param userId     用户ID
     * @param agencyId   机构ID
     * @param agencyName 机构名称
     * @return 影响的行数
     */
    @Override
    public int updateAgency(String userId, int agencyId, String agencyName) {
        return userMapper.updateAgency(userId, String.valueOf(agencyId), agencyName);
    }

    /**
     * 批量新增用户，userId和username存在时进行更新
     * 主要用于：同步基础平台的用户到本地用户表，回显审核人姓名与头像时不从基础平台获取，减少延迟
     * @param userList 用户列表
     * @return 影响的行数
     */
    @Override
    public Integer saveOrUpdateUser(List<User> userList) {
        return userMapper.saveOrUpdateUser(userList);
    }

    @Override
    public boolean usernameExist(String username) {
        // Controller中已做数据校验
//        if (!StringUtils.hasText(username)) {
        // 不允许使用空白符作为用户名，返回true，说明用户名已存在
//            return true;
//        }
        return userMapper.count("username", username) > 0;
    }

    @Override
    public boolean phoneNumberExist(String phoneNumber) {
        return userMapper.count("phone_number", phoneNumber) > 0;
    }

    /**
     * 根据用户类型查找用户
     * @param userType 用户类别
     * @param verified true注册了机构的用户
     * @return 用户列表
     */
    @Override
    public List<User> listUserByUserType(UserType userType, boolean verified) {
        return userMapper.listUserByUserType(userType, verified);
    }

    /**
     * 根据条件查询用户
     * @param param 查询料件
     * @return 用户列表
     */
    @Override
    public List<User> listUserByCondition(UserListParam param) {
        return userMapper.listUserByCondition(param);
    }

    /**
     * 根据用户ID获取用户信息
     * @param userIds 用户ID列表
     * @return 用户列表
     */
    public List<User> listUserByIds(List<String> userIds) {
        return userMapper.listUserByIds(userIds);
    }
}
