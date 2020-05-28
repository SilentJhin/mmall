package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisPoolUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;


public class RedisShardedPool {
    // sharded Jedis 连接池
    private static ShardedJedisPool pool;
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

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static int redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static int redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        // 连接耗尽的时候师傅阻塞 false：抛异常 true: 阻塞直到超时
        config.setBlockWhenExhausted(true);

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, 1000*2);
        // 设置密码
        //info1.setPassword();
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port, 1000*2);

        List<JedisShardInfo> jedisSentinelPoolList = new ArrayList<>(2);
        jedisSentinelPoolList.add(info1);
        jedisSentinelPoolList.add(info2);

        pool = new ShardedJedisPool(config, jedisSentinelPoolList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);

    }

    static {
        initPool();
    }

    public static ShardedJedis getShardedJedis() {
        return pool.getResource();
    }

    public static void returnBreakenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(ShardedJedis jedis) {
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

















