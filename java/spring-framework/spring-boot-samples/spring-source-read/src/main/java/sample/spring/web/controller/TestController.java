package sample.spring.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.spring.web.service.IAccountService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有的测试接口都放在这里
 */
@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @Resource
    IAccountService service;

    /**
     * 测试Spring事务
     * @param param
     * @return
     */
    @GetMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> map(@RequestBody Map<String, Object> param) {
        try {
            int fromUserId = (int) param.get("fromUserId");
            int toUserId = (int) param.get("toUserId");
            int money = (int) param.get("money");
            service.transferMoney(fromUserId, toUserId, money);
        } catch (Exception exception) {
            Map<String, Object> in = new HashMap<>(param);
            param.clear();
            param.put("param", in);
            param.put("exception", exception.getMessage());
        }
        return param;
    }
}
