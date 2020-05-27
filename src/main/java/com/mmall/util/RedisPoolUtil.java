package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * Created by SilentJhin
 */
@Slf4j
public class RedisPoolUtil {

    public static String get (String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get error! key:{}  ", key, e);
            RedisPool.returnBreakenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String set (String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set error! key:{}  value:{} ", key, value, e);
            RedisPool.returnBreakenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    // set有效期的key 有效期单位是秒
    public static String setEx (String key, String value, int exTime) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setEx error! key:{}  value:{} ", key, value, e);
            RedisPool.returnBreakenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    // 重设key有效期
    public static Long expire (String key, int exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire error! key:{}  exTime:{} ", key, exTime, e);
            RedisPool.returnBreakenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long del (String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del error! key:{}", key, e);
            RedisPool.returnBreakenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

}
