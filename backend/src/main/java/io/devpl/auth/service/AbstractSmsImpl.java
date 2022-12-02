package io.devpl.auth.service;

import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.devpl.auth.config.SmsProperties;
import io.devpl.auth.domain.SMSResult;
import io.devpl.auth.domain.SmsCode;
import io.devpl.auth.mapper.SmsCodeMapper;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 短信服务抽象类Abstract Short Message Service Implementation
 * 采用模板模式，定义验证码的发送流程，由子类执行实际的发送
 * @author baishengwen, Xu Jiabao
 * @version 1.0
 * @since 2022/2/9 16:06
 */
public abstract class AbstractSmsImpl implements IShortMsgService {

    private final SmsCodeMapper smsCodeMapper;
    private final SmsProperties smsProperties;
    private final ISaltService saltService;

    public AbstractSmsImpl(SmsCodeMapper smsCodeMapper, SmsProperties smsProperties,
                           ISaltService saltService) {
        this.smsCodeMapper = smsCodeMapper;
        this.smsProperties = smsProperties;
        this.saltService = saltService;
    }

    /**
     * 校验验证码
     * @param phone 手机号
     * @param code 验证码，Hash(Salt + Hash(phone + code))
     * @param token 找到加密盐的Token
     * @return 1正确，0错误，-1已过期
     */
    @Override
    public int checkCode(String phone, String code, String token) {
        // 验证码为空，手机号非法返回0
        if (code == null || !PhoneUtil.isMobile(phone)) {
            return 0;
        }
        LambdaQueryWrapper<SmsCode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SmsCode::getPhone, phone);
        SmsCode smsCode = smsCodeMapper.selectOne(wrapper);
        // 没有关联的验证码
        if (smsCode == null)
            return 0;
        // token 无效返回null
        String digest = saltService.md5Digest(token, smsCode.getCode());
        // 验证码错误
        if(!code.equals(digest))
            return 0;
        if(smsCode.getExpireTime().isBefore(LocalDateTime.now())){
            // 验证码过期
            return -1;
        }
        return 1;
    }

    /**
     * 发送短信验证码。执行逻辑：
     * 1、查找为该手机号发送短信的记录。
     * 2、如果结果为空，说明没有发送过，那么就发送短信
     * 3、如果结果不为空：且在配置的短信发送间隔内，则不发送
     * 4、如果结果不为空：且已过期（已超过配置的时间间隔），则发送
     * 5、如果发送验证码，保存到数据库中
     * @param phone 手机号
     */
    @Override
    public final SMSResult sendSmsCode(String phone) {
        // 判断是否应该发送验证码
        LambdaQueryWrapper<SmsCode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SmsCode::getPhone, phone);
        SmsCode smsCode = smsCodeMapper.selectOne(wrapper);
        // 发送验证码的情况：结果为空（没发送过）；或验证码已过期；或超过发送间隔
        LocalDateTime now = LocalDateTime.now();
        if (smsCode == null || smsCode.getExpireTime().isBefore(now)
                || smsCode.getCreateTime().plusSeconds(smsProperties.getSendInterval()).isBefore(now)) {
            // 生成随机六位数的验证码
            String code= RandomUtil.randomNumbers(6);
            if (doSendCode(phone, code)) {
                // 验证码MD5摘要（前面拼接手机号（加盐），与密码保存方式一致），保存到数据库
                String encryptedCode = phone + code;
                encryptedCode = DigestUtils.md5DigestAsHex(encryptedCode.getBytes(StandardCharsets.UTF_8));
                if (smsCode == null) {
                    smsCode = new SmsCode();
                    smsCode.setPhone(phone);
                    smsCode.setCode(encryptedCode);
                    smsCode.setCreateTime(now);
                    smsCode.setExpireTime(now.plusMinutes(smsProperties.getExpireAfter()));
                    smsCodeMapper.insert(smsCode);
                } else {
                    smsCode.setCode(encryptedCode);
                    smsCode.setCreateTime(now);
                    smsCode.setExpireTime(now.plusMinutes(smsProperties.getExpireAfter()));
                    smsCodeMapper.updateById(smsCode);
                }
                return SMSResult.SUCCESS;
            }
            return SMSResult.FAIL;
        }
        // 不发送
        return SMSResult.WAIT;
    }

    /**
     * 实际执行验证码发送，调用各个云平台提供的服务实现
     * @param phone 手机号
     * @param code 验证码
     */
    protected abstract boolean doSendCode(String phone, String code);

}
