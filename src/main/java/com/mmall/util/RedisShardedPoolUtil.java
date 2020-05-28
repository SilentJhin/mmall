package com.mmall.util;

import com.mmall.common.RedisPool;
import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by SilentJhin
 */
@Slf4j
public class RedisShardedPoolUtil {

    public static String get (String key) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getShardedJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get error! key:{}  ", key, e);
            RedisShardedPool.returnBreakenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static String set (String key, String value) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getShardedJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set error! key:{}  value:{} ", key, value, e);
            RedisShardedPool.returnBreakenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    // set有效期的key 有效期单位是秒
    public static String setEx (String key, String value, int exTime) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getShardedJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setEx error! key:{}  value:{} ", key, value, e);
            RedisShardedPool.returnBreakenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    // 重设key有效期
    public static Long expire (String key, int exTime) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getShardedJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire error! key:{}  exTime:{} ", key, exTime, e);
            RedisShardedPool.returnBreakenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static Long del (String key) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getShardedJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del error! key:{}", key, e);
            RedisShardedPool.returnBreakenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

}
