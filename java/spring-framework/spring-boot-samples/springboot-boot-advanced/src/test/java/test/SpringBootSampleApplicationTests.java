package test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.sample.SpringBootSampleApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSampleApplication.class)
@WebAppConfiguration
public class SpringBootSampleApplicationTests {

    @Test
    public void contextLoads() {
    }

}
