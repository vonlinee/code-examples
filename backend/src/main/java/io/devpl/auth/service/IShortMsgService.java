package io.devpl.auth.service;

import io.devpl.auth.domain.ComplaintContact;
import io.devpl.auth.domain.SMSResult;

/**
 * 短信验证码、短信通知Service接口（SMS：Short Message Service）
 * @author baishengwen
 * @author Xu Jiabao
 * @version 1.0
 * @since 2022/2/9 15:59
 */
public interface IShortMsgService {

    /**
     * 校验验证码
     * @param phone 手机号
     * @param code 验证码，Hash(Salt + Hash(phone + code))
     * @param token 找到加密盐的Token
     * @return 1正确，0错误，-1已过期
     */
    int checkCode(String phone, String code, String token);

    /**
     * 发送短信验证码。执行逻辑：
     * 1、查找为该手机号发送短信的最近一条记录。
     * 2、如果结果为空，说明没有发送过，那么就发送短信
     * 3、如果结果不为空：如果在配置的时间间隔内已发送过，则不发送
     * 4、如果结果不为空：如果距离上一次发送时间已超过配置的时间间隔，那么发送
     * @param phone 手机号
     * @return 短信发送结果
     */
    SMSResult sendSmsCode(String phone);

    /**
     * 根据短信模板代码获取模板内容
     * @param templateCode 短信模板内容
     * @return 完整短信内容
     */
    String getTemplateContentByCode(String templateCode);

    /**
     * 发送投诉整改审核通过的短信通知
     * @param contact 投诉联系人信息
     * @return 短信发送结果
     */
    SMSResult sendComplaintNotice(ComplaintContact contact);
}
