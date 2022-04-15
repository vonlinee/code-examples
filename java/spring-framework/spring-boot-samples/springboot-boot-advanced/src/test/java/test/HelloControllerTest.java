package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.spring.boot.SpringBootSampleApplication;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年2月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSampleApplication.class)
//@WebAppConfiguration // 使用@WebIntegrationTest注解需要将@WebAppConfiguration注释掉
// @WebIntegrationTest("server.port:0")// 使用0表示端口号随机，也可以具体指定如8888这样的固定端口 将springboot版本降级到1.4.0并使用WebIntegrationTest(不推荐)
public class HelloControllerTest {

    private String dateReg;
    private Pattern pattern;
    private TestRestTemplate template = new TestRestTemplate();
    @Value("${local.server.port}")// 注入端口号
    private int port;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // @Rule // 这里注意，使用@Rule注解必须要用public
    // public OutputCapture capture = new OutputCapture();

    /**
     * @throws Exception
     * @author SHANHY
     * @create 2016年2月23日
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // 构造RestTemplate实例
//		template = new RestTemplate();
//		List<>HttpMessageConverter<>?>> messageConverters = new ArrayList<>>();
//		messageConverters.add(new StringHttpMessageConverter());
//		messageConverters.add(new FormHttpMessageConverter());
//		template.setMessageConverters(messageConverters);
    }

    /**
     * @throws Exception
     * @author SHANHY
     * @create 2016年2月23日
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws Exception
     * @author SHANHY
     * @create 2016年2月23日
     */
    @Before
    public void setUp() throws Exception {
        dateReg = "^\\d{4}(\\-\\d{1,2}){2}";
        pattern = Pattern.compile(dateReg);
    }

    /**
     * @throws Exception
     * @author SHANHY
     * @create 2016年2月23日
     */
    @After
    public void tearDown() throws Exception {
    }

    @Ignore("忽略运行")
    @Test
    public void getInfo() {

    }

    //	timeout测试是指在指定时间内就正确
    @Test(timeout = 500)
    public void verifyReg() {
        Matcher matcher = pattern.matcher("2010-10-2");
        boolean isValid = matcher.matches();
//		静态导入功能
        assertTrue("pattern is not match", isValid);
    }

    @Test(expected = RuntimeException.class)
    public void test2() {
        throw new RuntimeException();
    }

    @Test
    public void test3() {
        // template的GET请求方式
        String url = "http://localhost:" + port + "/myspringboot/hello/info.json?name={name}&name1={name1}";
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        map.put("name1", "Lily");
        String result = template.getForObject(url, String.class, map);
        logger.info("Template Get Method >> result = {} ", result);

        // template的POST请求方式
//        url = "http://localhost:"+port+"/myspringboot/hello/info.json";
        result = template.postForObject(url, null, String.class, map);
        logger.info("Template Post Method >> result = {} ", result);
        assertNotNull(result);
        assertThat(result, Matchers.containsString("Tom"));
    }

    @Test
    public void test4() {
        System.out.println("HelloWorld");
        logger.info("logo日志也会被capture捕获测试输出");
        // assertThat(capture.toString(), Matchers.containsString("World"));
    }
}
