package com.ruoyi.web.controller.system;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import io.devpl.spring.web.mvc.RequestInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 首页
 * @author ruoyi
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Resource
    private RuoYiConfig ruoyiConfig;

    /**
     * 访问首页，提示语
     */
    @GetMapping("/")
    public String index(RequestInfo info) {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", ruoyiConfig.getName(), ruoyiConfig.getVersion());
    }
}
