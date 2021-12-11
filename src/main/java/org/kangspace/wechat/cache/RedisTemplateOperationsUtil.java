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
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/10/16 13:50
 */
public class RedisTemplateOperationsUtil {

    /**
     * 当key不存在时设置值
     * @param redisTemplate redisTemplate
     * @param key key
     * @param value  value
     * @param timeout timeout
     * @param unit unit
     * @return Boolean
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
                                    /* if (isJedisConvertersSpringBoot1Version()) {
                                         byte[] nxxx = JedisConverters.toSetCommandNxXxArgument(RedisStringCommands.SetOption.ifAbsent());
                                         byte[] expx = JedisConverters.toSetCommandExPxArgument(expiration);
                                         return "OK".equals(((BinaryJedisCluster) (connection.getNativeConnection())).set(rawKey, rawValue, nxxx, expx, expiration.getExpirationTime()));
                                     }else if(connection instanceof RedisStringCommands){
                                         return connection.set(rawKey,rawValue,expiration,RedisStringCommands.SetOption.ifAbsent());
                                     }*/
                                     return connection.set(rawKey,rawValue,expiration,RedisStringCommands.SetOption.ifAbsent());
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                                 return false;
                             }
                         },
                        true);
    }

    private static boolean isJedisConvertersSpringBoot1Version(){
        try {
            Method method = JedisConverters.class.getDeclaredMethod("toSetCommandNxXxArgument", RedisStringCommands.SetOption.class);
            return method.getReturnType()!=null && method.getReturnType().isArray();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
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

