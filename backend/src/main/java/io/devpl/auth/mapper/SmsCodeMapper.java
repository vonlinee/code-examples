package io.devpl.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.devpl.auth.domain.SmsCode;
import org.springframework.stereotype.Repository;

/**
 * 短信验证码DAO
 * @author baishengwen
 * @version 1.0
 * @since 2022/2/9 15:56
 */
@Repository
public interface SmsCodeMapper extends BaseMapper<SmsCode> {

}
