package io.maker.codegen.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import freemarker.template.TemplateException;
import io.maker.codegen.service.GeneratorService;
import io.maker.codegen.utils.FreemarkerUtil;
import io.maker.codegen.utils.MapUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * GeneratorService
 *
 * @author zhengkai.blog.csdn.net
 */
@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {

    String templateCpnfig = null;

    /**
     * 从项目中的JSON文件读取String
     *
     * @author zhengkai.blog.csdn.net
     */
    @Override
    public String getTemplateConfig() throws IOException {
        templateCpnfig = null;
        if (templateCpnfig != null) {
        } else {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template.json");
            templateCpnfig = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            inputStream.close();
        }
        //log.info(JSON.toJSONString(templateCpnfig));
        return templateCpnfig;
    }

    /**
     * 根据配置的Template模板进行遍历解析，得到生成好的String
     *
     * @author zhengkai.blog.csdn.net
     */
    @Override
    public Map<String, String> getResultByParams(Map<String, Object> params) throws IOException, TemplateException {
        Map<String, String> result = new HashMap<>(32);
        result.put("tableName", MapUtil.getString(params,"tableName"));
        JSONArray parentTemplates = JSONArray.parseArray(getTemplateConfig());
        for (int i = 0; i <parentTemplates.size() ; i++) {
            JSONObject parentTemplateObj = parentTemplates.getJSONObject(i);
            for (int x = 0; x <parentTemplateObj.getJSONArray("templates").size() ; x++) {
                JSONObject childTemplate = parentTemplateObj.getJSONArray("templates").getJSONObject(x);
                result.put(childTemplate.getString("name"), FreemarkerUtil.processString(parentTemplateObj.getString("group") + "/" +childTemplate.getString("name")+ ".ftl", params));
            }
        }
        return result;
    }

}
