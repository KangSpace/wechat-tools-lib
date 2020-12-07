package org.kangspace.wechat.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;
import redis.clients.jedis.BinaryJedisCluster;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2020/10/16 13:50
 */
public class RedisTemplateOperationsUtil {

    /**
     * 当key不存在时设置值
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public static <K,V> Boolean setIfAbsent(RedisTemplate<K, ?> redisTemplate, K key, V value, long timeout, TimeUnit unit) {
        final byte[] rawKey = getRawKey(redisTemplate,key);
        final byte[] rawValue = rawValue(redisTemplate,value);
        final Expiration expiration = Expiration.from(timeout, unit);
        return redisTemplate
                .execute(new RedisCallback<Boolean>() {
                             @Override
                             public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                                 try {
                                     byte[] nxxx = JedisConverters.toSetCommandNxXxArgument(RedisStringCommands.SetOption.ifAbsent());
                                     byte[] expx = JedisConverters.toSetCommandExPxArgument(expiration);
                                     return "OK".equals(((BinaryJedisCluster) (connection.getNativeConnection())).set(rawKey, rawValue, nxxx, expx, expiration.getExpirationTime()));
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                     return false;
                                 }
                             }
                         },
                        true);
    }

    public static <K,V> byte[] getRawKey(RedisTemplate<K, ?> redisTemplate, Object key) {
        Assert.notNull(key, "non null key required");
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        if (keySerializer == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return keySerializer.serialize(key);
    }

    public static <K,V> byte[]  rawValue(RedisTemplate<K, ?> redisTemplate, Object value) {
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        if (valueSerializer == null && value instanceof byte[]) {
            return (byte[]) value;
        }
        return valueSerializer.serialize(value);
    }

}

