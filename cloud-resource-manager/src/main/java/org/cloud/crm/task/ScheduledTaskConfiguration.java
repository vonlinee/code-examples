package org.cloud.crm.task;

import lombok.extern.slf4j.Slf4j;
import org.cloud.crm.utils.DateTimeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableScheduling
@Configuration
public class ScheduledTaskConfiguration {

    static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 5000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    /**
     * <a href="https://support.huaweicloud.com/api-ces/ces_03_0034.html">...</a>
     * 单次POST请求消息体大小不能超过512KB，否则请求会被服务端拒绝。
     * 对于不同的period取值和查询的指标数量，其对应的默认最大查询区间(to - from)也不同，计算规则为“指标数量 * (to - from) / 监控周期 ≤ 3000”。
     * 当period值为“1”时，监控周期为60*1000ms=60000ms(60s/1分钟)。
     * 当period值为"300" 时，监控周期为300*1000ms=300000ms。
     * 当period值为"1200" 时，监控周期为1200*1000ms=1200000ms。
     * 当period值为"3600" 时，监控周期为3600*1000ms=3600000ms。
     * 当period值为"14400" 时，监控周期为14400*1000ms=14400000ms。
     * 当period值为"86400" 时，监控周期为86400*1000ms=86400000ms。
     * 例如批量查询300个指标，监控周期为60000ms，可算出(to - from)最大值为"600000"，若设定的请求参数使(to - from)超出最大值，from值会自动调整为"to-600000"。
     * 每5秒执行一次
     */
    // @Scheduled(cron = "*/1 * * * * ?")
    public void test() {
        LocalTime now = LocalTime.now();
        LocalDate date = LocalDate.now();
        log.info("定时任务执行");
        // 同时开5个线程，获取5分钟内的每分钟的数据
        for (int i = 1; i < 5; i++) {
            final int index = i;
            executor.execute(() -> {
                // 查询数据截止时间UNIX时间戳，单位毫秒。from必须小于to。
                long to = now.minusMinutes(index).atDate(date).toInstant(ZoneOffset.UTC).toEpochMilli();

                System.out.println(Thread.currentThread().getName() + " " + DateTimeUtils.nowTime() + " " + to);
            });
        }

    }

    public static void main(String[] args) {

        executor.setThreadFactory(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        System.out.println("handle exception");
                    }
                });
                return thread;
            }
        });

        executor.execute(() -> {
            while (true) {
                // 检测中断状态，决定是否退出线程
                if (Thread.interrupted()) {
                    break;
                }
            }
        });
    }
}
