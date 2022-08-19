package org.example.springboot.controller;

import com.google.common.util.concurrent.RateLimiter;

import org.example.springboot.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

@RestController
@RequestMapping("/concurrency")
public class ConcurrencyController {
    private static final Logger log = LoggerFactory.getLogger(ConcurrencyController.class);
    // 最大允许并发数为10，使用公平锁
    private final Semaphore permit = new Semaphore(10, true);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        try {
            permit.acquire();
            log.info("处理请求===============>");
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error("error");
        } finally {
            permit.release();
        }
        return "success";
    }


    public static void main(String[] args) {
        String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        RateLimiter limiter = RateLimiter.create(1.0); // 这里的1表示每秒允许处理的量为1个
        for (int i = 1; i <= 10; i++) {
            double waitTime = limiter.acquire(i);// 请求RateLimiter, 超过permits会被阻塞
            long millis = System.currentTimeMillis();
            System.out.println("cutTime=" + millis + " call execute:" + i + " waitTime:" + waitTime);
        }
        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("start time:" + start);
        System.out.println("end time:" + end);
    }
}
