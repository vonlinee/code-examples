package io.devpl.auth.service;

import io.devpl.auth.domain.Identity;
import io.devpl.auth.mapper.IdentityMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户身份管理
 */
@Service
public class IdentityServiceImpl implements IIdentityService {

    private final IdentityMapper identityMapper;

    public IdentityServiceImpl(IdentityMapper identityMapper) {
        this.identityMapper = identityMapper;
    }

    /**
     * 根据用户ID获取身份/角色，基础平台和本系统用户统一调用本接口
     * 检查某用户是否有权限时，应通过Subject subject = SecurityUtils.getSubject()判断角色和权限
     * @param userId 用户ID
     * @param token 登录令牌，不为null时代表基础平台用户
     * @param moduleId 模块ID，基础平台用户有效
     * @return 身份列表
     */
    @Override
    public Identity listIdentityByUserId(String userId, String token, String moduleId) throws Exception {
        if (StringUtils.hasText(token)) {
            // 基础平台用户
           return null;
        } else {
            // 本系统用户（门户网站注册的用户）
            // result = identityMapper.listIdentityByUserId(userId);
            return null;
        }
    }

    /**
     * 设置用户身份
     * @param userId 用户ID
     * @param IdentityCode 身份代码
     * @return 影响的行数
     */
    @Override
    public int setIdentity(String userId, String IdentityCode) {
        return identityMapper.setIdentity(userId, IdentityCode);
    }

    /**
     * 查询身份
     * @param param 查询参数
     * @return 身份列表
     */
    @Override
    public List<Identity> listByCondition(IdentityListParam param) {
        return identityMapper.listByCondition(param);
    }
}
