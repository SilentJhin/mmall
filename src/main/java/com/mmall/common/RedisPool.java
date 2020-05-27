package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @date 0522 1641
 */
public class RedisPool {
    // Jedis 连接池
    private static JedisPool pool;
    // 最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));
    // 在Jedis连接池里最大的状态为空闲(idle)的Jedis实例的个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));
    // 在Jedis连接池里最小的状态为空闲(idle)的Jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));
    // 从Jedis连接池里拿取一个实例时是否要进行验证操作 如果为true 则拿到的实例一定是可用的
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    // 还给Jedis连接池的实例时是否要进行验证操作 如果为true 则还的的实例一定是可用的
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");

    private static int redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        // 连接耗尽的时候师傅阻塞 false：抛异常 true: 阻塞直到超时
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config, redisIp, redisPort, 1000*2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnBreakenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        RedisPoolUtil.set("ktest1", "vtest2");
        String str = RedisPoolUtil.get("ktest1");
        RedisPoolUtil.setEx("ktest2", "vtest2", 60);
        RedisPoolUtil.expire("ktest1", 30);
        RedisPoolUtil.del("ktest2");

    }

}

















