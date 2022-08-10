package sample.redis.jedis.lock;

import redis.clients.jedis.Jedis;

import static sample.redis.jedis.lock.Utils.sleepThreadSeconds;

/**
 * @author vonline
 * @since 2022-07-29 17:40
 */
public class RedisLockImpl extends RedisLock {

    private boolean isExpired(String lockValue) {
        return currentUnixStamp() > Long.parseLong(lockValue.split("-")[1]);
    }

    private String clientName() {
        return Thread.currentThread().getName();
    }

    @Override
    protected void lock(Jedis jedis, String lockKey) {
        while (jedis.setnx(lockKey, createLockValue()) <= 0) {
            String value = jedis.get(lockKey);
            // 检查是否过期
            if (isExpired(value)) {
                // 此处应该根据当前时间生成值
                String newLockValue = createLockValue();
                // GETSET lock.foo <current Unix timestamp + lock timeout + 1>
                if (isExpired(jedis.getSet(lockKey, newLockValue))) {
                    break; // 获取锁成功
                }
            }
            System.out.println("锁被" + value.split("-")[0] + "持有，" + Thread.currentThread().getName() + "重试");
            sleepThreadSeconds(2); // 重试
        }
        System.out.println(clientName() + "获取锁成功");
    }


    private void lock1(Jedis jedis, String lockKey) {

    }



    /* 一个错误实现
    @Override
    protected void lock(Jedis jedis, String lockKey) {
        String lockValue = createLockValue();
        boolean acquired = false;
        int retryTimes = 0;
        // 下面L1和L2这里有问题
        while (jedis.setnx(lockKey, lockValue) <= 0) {    // L1
            String lockValue1 = jedis.get(lockKey);   // L2
            if (lockValue1 == null) System.out.println("================");
            // 检查是否过期
            while (isExpired(lockValue1)) {
                // 此处应该根据当前时间生成值
                String newLockValue = createLockValue();
                // GETSET lock.foo <current Unix timestamp + lock timeout + 1>
                if (isExpired(jedis.getSet(lockKey, newLockValue))) {
                    break; // 获取锁成功
                }
                // 重试
                sleepThreadSeconds(2);
            }
        }
        System.out.println(clientName() + "获取锁成功");
    }
    */
    @Override
    protected void unlock(Jedis jedis, String lockKey) {
        if (!isExpired(jedis.get(lockKey))) {
            long result = jedis.del(lockKey);
            if (result >= 0) {
                System.out.println(clientName() + "释放锁成功");
            }
        } else {
            System.out.println(clientName() + " => 未过期，释放锁失败");
        }
    }
}
