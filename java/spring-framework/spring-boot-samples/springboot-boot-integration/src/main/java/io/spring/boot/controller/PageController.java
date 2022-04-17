package io.spring.boot.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Controller
public class PageController implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(PageController.class);

    // 从 application.properties 中读取配置，如取不到默认值为Hello Shanhy
    @Value("${application.hell:Hello Shanhy}")
    private String hello = "Hello Shanhy";

    /**
     * 默认页<br/>
     * @return
     * @RequestMapping("/") 和 @RequestMapping 是有区别的
     * 如果不写参数，则为全局默认页，加入输入404页面，也会自动访问到这个页面。
     * 如果加了参数“/”，则只认为是根页面。
     * @author SHANHY
     * @create 2016年1月5日
     */
    @RequestMapping(value = {"/", "/index"})
    public String index(Map<String, Object> model) {
        // 直接返回字符串，框架默认会去 spring.view.prefix 目录下的 （index拼接spring.view.suffix）页面
        // 本例为 /WEB-INF/jsp/index.jsp
        model.put("time", new Date());
        model.put("message", this.hello);

        return "index";
    }

    /**
     * 响应到JSP页面page1
     * @return
     * @author SHANHY
     * @create 2016年1月5日
     */
    @RequestMapping("/page1")
    public ModelAndView page1() {
        log.info(">>>>>>>> PageController.page1");
        // 页面位置 /WEB-INF/jsp/page/page.jsp
        ModelAndView mav = new ModelAndView("page/page1");
        mav.addObject("content", hello);
        return mav;
    }

    /**
     * 响应到JSP页面comet
     * @return
     * @author SHANHY
     */
    @RequestMapping("/comet")
    public ModelAndView comet() {
        ModelAndView mav = new ModelAndView("comet");
        return mav;
    }

    @RequestMapping("/async/test")
    @ResponseBody
    public Callable<String>

    callable() {
        // 这么做的好处避免web server的连接池被长期占用而引起性能问题，
        // 调用后生成一个非web的服务线程来处理，增加web服务器的吞吐量。
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3 * 1000L);
                return "小单 - " + System.currentTimeMillis();
            }
        };
    }

    @RequestMapping("/testJson")
    @ResponseBody
    public Map<String, String>

    getInfo(@RequestParam(required = false) String name,
            @RequestParam(required = false) String name1) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("name1", name1);
        return map;
    }

    /**
     * 输入 和输出为JSON格式的数据的方式 HttpEntity<?> ResponseEntity<?>
     */
    @ResponseBody
    @RequestMapping(value = "/testJson2", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> jsonTest4(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String name1, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("name1", name1);

        // 这里直接new一个对象（HttpHeaders headers = new HttpHeaders();）
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("MyHeaderName", "SHANHY");

        List<String> domainList = new ArrayList<>();
        domainList.add("http://www.domain1.com");
        domainList.add("http://www.domain2.com");
        domainList.add("http://localhost:8088");
        String requestDomain = request.getHeader("origin");
        log.info("requestDomain = " + requestDomain);
        if (domainList.contains(requestDomain)) {
            response.addHeader("Access-Control-Allow-Origin", requestDomain);
            response.addHeader("Access-Control-Allow-Methods", "GET");
            response.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        }

        ResponseEntity<Map<String, String>> responseResult = new ResponseEntity<>(map, responseHeaders, HttpStatus.OK);
        return responseResult;
    }

    /**
     * 响应到JSP页面page1（可以直接使用Model封装内容，直接返回页面字符串）
     * @return
     * @author SHANHY
     * @create 2016年1月5日
     */
    @RequestMapping("/page2")
    public String page2(Model model) {
        // 页面位置 /WEB-INF/jsp/page/page.jsp
        model.addAttribute("content", hello + "（第二种）");

        // 下面是测试EhCache的
        String cacheContent = "";
        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache = cacheManager.getCache("testCache");
        Element element = cache.get("key");
        if (element == null) {
            element = new Element("key", "你好，缓存内容！" + System.currentTimeMillis());
            cache.put(element);
        }
        cacheContent = element.getValue().toString();
        model.addAttribute("content", hello + "（第二种）" + cacheContent);

        return "page/page1";
    }

    @Override
    public void setEnvironment(Environment environment) {
        String s = environment.getProperty("JAVA_HOME");
        System.out.println(s);
    }
}
