package org.springboot.sample.config.viewresolver.view;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class MMappingJackson2JsonView extends MappingJackson2JsonView {

    /**
     * 排除JSON转换的时候 model 中自动加入的对象<br/>
     * 如果你在项目中使用了 @ControllerAdvice , 要特别注意了，我们在这里就是要排除掉因为@ControllerAdvice自动加入的值
     */
    @Override
    protected Object filterModel(Map<String, Object> model) {
        Map<String, Object> result = new HashMap<String, Object>(model.size());
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            if (!"urls".equals(entry.getKey())) {// 对我在项目中使用 @ControllerAdvice 统一加的值，进行排除。
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return super.filterModel(result);
    }
}