package io.devpl.auth.service;

import io.devpl.auth.domain.User;
import io.devpl.auth.domain.UserType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理从基础平台返回的用户XML，转换成用户列表
 * @author Xu Jiabao
 * @since 2022/4/16
 */
public class BaseUserXmlHandler extends DefaultHandler {

    private List<User> result;
    private User current;
    private String currentContent;
    // 每个用户数据的开始标签
    private static final String START_LABEL = "anyType";

    public List<User> getResult() {
        return result;
    }

    @Override
    public void startDocument() throws SAXException {
        // 变量初始化
        result = new ArrayList<>(50);
        current = new User();
    }

    /**
     * 元素解析开始前的回调函数
     * @param uri 命名空间
     * @param localName 标签名
     * @param qName 带命名空间的标签名
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(START_LABEL)) {
            current = new User();
        }
    }

    /**
     * 解析标签内容时的回调函数
     * @param ch 字符数组
     * @param start 字符开始索引
     * @param length 内容长度
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        currentContent = new String(ch, start, length).trim();
    }

    /**
     * 元素解析完成后的回调函数
     * @param uri 命名空间
     * @param localName 标签名
     * @param qName 带命名空间的标签名
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "anyType":
                result.add(current);
                break;
            case "UserID":
                // 基础平台的UserId是本系统的UserId和Username
                current.setUserId(currentContent);
                current.setUsername(currentContent);
                break;
            case "UserName":
                // 基础平台的UserName（中文名）是本系统的ShortName
                current.setShortName(currentContent);
                break;
            case "UserType":
                current.setGender("");
            case "PhotoPath":
                current.setEnable(false);
                break;
        }
    }

}
