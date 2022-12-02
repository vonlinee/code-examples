package io.devpl.auth.mapper;

import io.devpl.auth.domain.Identity;
import io.devpl.auth.service.IdentityListParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 身份管理DAO
 */
@Mapper
public interface IdentityMapper {

    /**
     * 根据用户ID获取身份列表
     * @param userId 用户ID
     * @return 身份列表
     */
    List<Identity> listIdentityByUserId(String userId);

    /**
     * 设置用户身份
     * @param userId 用户ID
     * @param IdentityCode 身份代码
     * @return 影响的行数
     */
    int setIdentity(@Param("userId") String userId, @Param("identityCode") String IdentityCode);

    /**
     * 查询身份
     * @param param 查询参数
     * @return 身份列表
     */
    List<Identity> listByCondition(IdentityListParam param);
}
