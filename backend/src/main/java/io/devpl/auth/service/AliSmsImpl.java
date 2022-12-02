package io.devpl.auth.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySmsTemplateRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySmsTemplateResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import io.devpl.auth.config.SmsProperties;
import io.devpl.auth.domain.ComplaintContact;
import io.devpl.auth.domain.SMSResult;
import io.devpl.auth.domain.SmsTemplate;
import io.devpl.auth.mapper.SmsCodeMapper;
import io.devpl.auth.mapper.SmsTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务实现类，默认
 * 同一签名，同一手机号码发送验证码，最多1条/分钟，5条/小时，10条/天
 * 同一签名，同一手机号码发送短信通知，50条/天
 *
 * @author Xu Jiabao
 * @since 2022/3/22
 */
@Service
@ConditionalOnProperty(name = "sms.type", havingValue = "ali", matchIfMissing = true)
public class AliSmsImpl extends AbstractSmsImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliSmsImpl.class);
    private final SmsTemplateMapper smsTemplateMapper;
    private final SmsProperties smsProperties;
    private final IAcsClient client;

    public AliSmsImpl(SmsCodeMapper smsCodeMapper, SmsTemplateMapper smsTemplateMapper,
                      SmsProperties smsProperties, ISaltService saltService) {
        super(smsCodeMapper, smsProperties, saltService);
        this.smsProperties = smsProperties;
        this.smsTemplateMapper = smsTemplateMapper;

        // 从方法中提取公共代码，客户端只初始化一次
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                smsProperties.getAccessKey(), smsProperties.getSecret());
        this.client = new DefaultAcsClient(profile);
    }

    @Override
    public boolean doSendCode(String phone, String code) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(smsProperties.getCodeSign());
        request.setTemplateCode(smsProperties.getCodeTemplate());
        // 模板实际参数，JSON格式的字符串，Key与模板中定义的一致
        request.setTemplateParam("{\"code\":" + "'" + code + "'" + "}");
        try {
            // 返回参数详见 https://next.api.aliyun.com/document/Dysmsapi/2017-05-25/SendSms
            SendSmsResponse response = client.getAcsResponse(request);
            if (response.getCode().equals("OK")) {
                LOGGER.info("成功向手机号{}发送短信验证码{} 短信签名:{} 短信模板:{}",
                        phone, code, smsProperties.getCodeSign(), smsProperties.getCodeTemplate());
                return true;
            } else {
                // 分别是代码，消息，请求ID，回执
                LOGGER.error("短信验证码发送失败, code: {}, msg: {}, requestId: {}, BizId: {}",
                        response.getCode(), response.getMessage(), response.getRequestId(), response.getBizId());
                return false;
            }
        } catch (Exception exception) {
            LOGGER.error("短信发送异常", exception);
        }
        return false;
    }

    /**
     * 根据短信模板代码获取模板内容
     * @param templateCode 短信模板内容
     * @return 完整短信内容
     */
    @Override
    public String getTemplateContentByCode(String templateCode) {
        // 先从本地数据库中查找，找不到再调用阿里云接口获取，并保存到本地
        SmsTemplate smsTemplate = smsTemplateMapper.selectById(templateCode);
        if (smsTemplate != null)
            return smsTemplate.getContent();

        QuerySmsTemplateRequest request = new QuerySmsTemplateRequest();
        request.setTemplateCode(templateCode);
        try {
            QuerySmsTemplateResponse response = client.getAcsResponse(request);
            if (response.getCode().equals("OK")) {
                String templateContent = response.getTemplateContent();
                // 保存到本地
                SmsTemplate template = new SmsTemplate();
                template.setCode(templateCode);
                template.setContent(templateContent);
                smsTemplateMapper.insert(template);
                return templateContent;
            } else {
                LOGGER.error("获取短信模板内容异常。code: {} msg: {}", response.getCode(), response.getMessage());
                return "获取短信模板内容异常，请重试";
            }
        } catch (Exception exception) {
            LOGGER.error("获取短信模板内容异常", exception);
            throw new RuntimeException("获取短信模板内容异常，请重试");
        }
    }

    /**
     * 发送投诉整改审核通过的短信通知
     *
     * @param contact 投诉联系人信息
     * @return 短信发送结果
     */
    public SMSResult sendComplaintNotice(ComplaintContact contact) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(contact.getPhone());
        request.setSignName(smsProperties.getComplaintSign());
        request.setTemplateCode(smsProperties.getComplaintTemplate());
        // 模板实际参数，JSON格式的字符串，Key与模板中定义的一致
        request.setTemplateParam("{\"name\":\"" + contact.getName() + "\", \"agencyName\":\"" + contact.getAgencyName() + "\"}");
        try {
            // 返回参数详见 https://next.api.aliyun.com/document/Dysmsapi/2017-05-25/SendSms
            SendSmsResponse response = client.getAcsResponse(request);
            if (response.getCode().equals("OK")) {
                LOGGER.info("成功向手机号{}发送投诉整改通过通知短信 短信签名:{} 短信模板:{}",
                        contact.getPhone(), smsProperties.getComplaintSign(), smsProperties.getComplaintTemplate());
                return SMSResult.SUCCESS;
            } else {
                // 分别是代码，消息，请求ID，回执
                LOGGER.error("投诉整改通过通知短信发送失败, code: {}, msg: {}, requestId: {}, BizId: {}",
                        response.getCode(), response.getMessage(), response.getRequestId(), response.getBizId());
                return SMSResult.FAIL;
            }
        } catch (Exception exception) {
            LOGGER.error("短信发送异常", exception);
        }
        return SMSResult.FAIL;
    }

}
