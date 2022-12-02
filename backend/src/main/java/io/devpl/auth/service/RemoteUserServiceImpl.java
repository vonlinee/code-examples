package io.devpl.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.devpl.auth.config.ConfigProperties;
import io.devpl.auth.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * 局端用户管理Service，使用HttpClients发送请求
 * TODO 将HTTPClient全部换成RestTemplate
 * @author Xu Jiabao
 * @since 2022/1/26
 */
@Slf4j
@Service
public class RemoteUserServiceImpl implements IRemoteUserService {

    private final ConfigProperties configProperties;
    private final RestTemplate restTemplate;
    // MD5倒序的系统ID，避免每次调用基础平台的接口时都计算一次
    private final String accessToken;

    public RemoteUserServiceImpl(ConfigProperties configProperties, RestTemplate restTemplate) {
        this.configProperties = configProperties;
        this.restTemplate = restTemplate;
        this.accessToken = new StringBuilder(DigestUtils.md5DigestAsHex("hello world".getBytes(StandardCharsets.UTF_8))).reverse().toString();
    }

    /**
     * 调用基础平台接口，检查用户是否在线
     * @param token 登陆后的令牌
     * @return 在线为true
     */
    @Override
    public boolean checkOnline(String token) throws IOException{
        String url = configProperties.getBaseAddr() + Constants.Base.USER_MGR + "?method=TokenCheck&params="
                + configProperties.getSysId() + "&token=" + token;
        return ((JSONObject) JSON.parse(doHttpGet(url))).getBooleanValue("result");
    }

    /**
     * 退出登录
     * @param token 登陆后的令牌
     * @return 成功登出true
     */
    @Override
    public boolean logout(String token) throws IOException {
        String url = configProperties.getBaseAddr() + Constants.Base.USER_MGR + "?method=Logout&params="
                + configProperties.getSysId() + "&token=" + token;
        return ((JSONObject) JSON.parse(doHttpGet(url))).getBooleanValue("result");
    }

    /**
     * 获取登录用户信息
     * @param token 登陆后的令牌
     * @return 用户信息
     */
    @Override
    public BaseUser findUserByToken(String token) throws IOException {
        String url = configProperties.getBaseAddr() + Constants.Base.USER_MGR + "?method=GetUserInfo&params="
                + configProperties.getSysId() + "&token=" + token;
        return JSON.parseObject(doHttpGet(url), BaseUser.class);
    }

    /**
     * 执行HTTP GET请求，适用于返回格式是{error:flag,data:[{key:value}]}的请求
     * @param url 请求URL
     * @return JSON数据
     */
    private String doHttpGet(String url) throws IOException{
        // 配置请求
//        RequestConfig config = RequestConfig.custom()
//                .setConnectTimeout(2000)  // 连接超时时间，毫秒
//                .setConnectionRequestTimeout(2000)  // 请求超时时间
//                .setSocketTimeout(2000)  // Socket读写超时时间
//                .setRedirectsEnabled(true)  // 是否允许重定向
//                .build();

        HttpGet getMethod = new HttpGet(url);
        // getMethod.setConfig(config);
        try(CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(getMethod)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject object = (JSONObject) JSON.parse(EntityUtils.toString(response.getEntity()));
                if (object.getIntValue("error") == 0) {
                    return object.getString("data");
                } else {
                    throw new IOException(object.getString("data"));
                }
            } else {
                throw new IOException("基础平台连接异常 " + url);
            }
        }
    }

    /**
     * 基础平台用户：根据登录Token获取默认身份
     * @param token 登录令牌
     * @param userId 用户ID
     * @param moduleId 可选，模块ID
     * @return BaseIdentityType
     */
    @Override
    public BaseIdentityType getDefaultIdentityCodeAndImgByToken(String token, String userId, String moduleId) throws Exception {
        String url = configProperties.getBaseAddr() + Constants.Base.GET_IDENTITY_BY_ID
                + "?appid=" + configProperties.getSysId() + "&login_token=" + token + "&access_token=" + this.accessToken;
        HttpGet getMethod = new HttpGet(url);
        // Login Token放入请求头
        // getMethod.addHeader("Authorization:X-Token", token);
        try(CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(getMethod)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject object = (JSONObject) JSON.parse(EntityUtils.toString(response.getEntity()));
                if (object.getIntValue("StatusCode") == 200 && object.getIntValue("ErrCode") == 0) {
                    return object.getObject("Data", BaseIdentityType.class);
                } else {
                    throw new IOException(object.getString("Msg"));
                }
            } else {
                throw new IOException("基础平台连接异常 " + url);
            }
        }
    }

    /**
     * 根据身份代码获取身份详情
     * @param eduId           教育局ID
     * @param token           登录令牌
     * @param identityCodes   身份代码：多个使用,分割
     * @param returnModuleIds 是否返回可访问的模块ID
     * @return 身份列表（名称、背景图等）
     */
    @Override
    public List<BaseIdentityType> listIdentityTypeByCode(String eduId, String token, String identityCodes, boolean returnModuleIds) {
        String url = configProperties.getBaseAddr() + Constants.Base.GET_IDENTITY_TYPE_CODE
                + "?EduID={1}&IdentityCodes={2}&ReturnModuleIDs={3}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "X-Token=" + token);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<BaseIdentityResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, BaseIdentityResponse.class, eduId, identityCodes, returnModuleIds ? 1 : 0);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null
                && response.getBody().getStatusCode() != null && response.getBody().getStatusCode() == 200) {
            return response.getBody().getData();
        }
        return Collections.emptyList();
    }

    /**
     * 条件查询教育局领导（暂时使用领导充当审查员角色）
     * @param token 令牌
     * @param userId 用户ID
     * @param userName 用户名，模糊查询
     * @param updateTime 作用未知
     * @param dataModel 作用未知
     * @return User用户列表
     */
    @Override
    public List<User> listEduLeaderByCondition(String token, String userId, String userName, String updateTime, String dataModel) throws Exception{
        StringBuilder builder = new StringBuilder(configProperties.getBaseAddr() + Constants.Base.GET_EDU_LEADER + "?Token=" + token + "&UserId=");
        if (userId != null)
            builder.append(userId);
        builder.append("&UserName=");
        if (userName != null)
            builder.append(userName);
        builder.append("&UpdateTime=");
        if (updateTime != null)
            builder.append(updateTime);
        builder.append("&DataModel=");
        if (dataModel != null)
            builder.append(dataModel);

        BaseUserXmlHandler handler = new BaseUserXmlHandler();
        getAndParseXml(builder.toString(), handler);
        return handler.getResult();
    }

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
    @Override
    public List<User> listAdminByCondition(String token, String userId, String userName, String schoolId, String userType, String updateTime, String dataModel) throws Exception {
        StringBuilder builder = new StringBuilder(configProperties.getBaseAddr() + Constants.Base.GET_ADMIN + "?Token=" + token + "&UserId=");
        if (userId != null)
            builder.append(userId);
        builder.append("&UserName=");
        if (userName != null)
            builder.append(userName);
        builder.append("&SchoolID=");
        if (schoolId != null)
            builder.append(schoolId);
        builder.append("&UserType=");
        if (userType != null)
            builder.append(userType);
        builder.append("&UpdateTime=");
        if (updateTime != null)
            builder.append(updateTime);
        builder.append("&DataModel=");
        if (dataModel != null)
            builder.append(dataModel);

        BaseUserXmlHandler handler = new BaseUserXmlHandler();
        getAndParseXml(builder.toString(), handler);
        return handler.getResult();
    }

    /**
     * 发送请求并解析XML
     * @param url 访问URL
     * @param contentHandler XML内容处理器
     */
    private void getAndParseXml(String url, ContentHandler contentHandler) throws Exception {
        HttpGet getMethod = new HttpGet(url);
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(getMethod)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputSource source = new InputSource(response.getEntity().getContent());
                XmlParseUtil.parse(contentHandler, source);
            } else {
                throw new IOException("基础平台连接异常");
            }
        }
    }

}
