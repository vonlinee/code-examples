package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import io.spring.boot.SpringBootSampleApplication;
import io.spring.boot.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年2月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSampleApplication.class)
@WebAppConfiguration
public class StudentControllerTest {

    @Autowired
    private IStudentService studentService;

    @Test
    public void likeName() {
        assertArrayEquals(
                new Object[]{
                        studentService.likeName("小明2").size() > 0,
                        studentService.likeName("坏").size() > 0,
                        studentService.likeName("莉莉").size() > 0
                },
                new Object[]{
                        true,
                        false,
                        true
                }
        );
//		assertTrue(studentService.likeNameByDefaultDataSource("ERROR").size() > 0);
    }

}
