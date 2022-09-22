package io.devpl.sdk.test;

import io.devpl.sdk.support.spring.DevplApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
public class DevplSupportTest {

    @Test
    public void test1() {
        SpringApplication.run(DevplApplication.class);
    }
}
