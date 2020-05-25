package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.JedisPool;

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

}
