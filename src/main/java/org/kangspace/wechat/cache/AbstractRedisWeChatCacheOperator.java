package org.kangspace.wechat.cache;

import org.apache.commons.lang.StringUtils;
import org.kangspace.wechat.util.JacksonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 微信相关Redis缓存抽象实现
 * 基于RedisTemplate缓存,需在Spring中注册redisTemplate
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2020/11/4 11:06
 */
public abstract class AbstractRedisWeChatCacheOperator<T extends AbstractWeChatCacheOperator.ExpireValue<V>, V> extends AbstractWeChatCacheOperator<T, V> {
    /**
     * <pre>
     * token缓存key字符串
     * 格式 {cacheKeyPrefix}:{@value TOKENS_CACHE_KEY_TAG}{AppID}
     * </pre>
     */
    public static final String TOKENS_CACHE_KEY_TAG = "tokens:";
    /**
     * 同步锁的value
     */
    private static final String LOCK_VAL = "1";
    /**
     * token过期空隙时间,如token有效期为10分钟,则设置缓存为 10*60 -{@value TOKEN_EXPIRE_GAP_SECOND_TIME} 分钟
     */
    private static final long TOKEN_EXPIRE_GAP_SECOND_TIME = 3L * 60;
    /**
     * 缓存key前缀
     */
    private String cacheKeyPrefix;
    /**
     * <pre>
     * 缓存超时时间,单位s:
     * 若getRaw()对象中expiresSeconds为null时,尝试使用该值
     * </pre>
     */
    private Long expiresSeconds;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 通过完整key获取同步锁Key
     *
     * @param fullKey
     * @return
     */
    private String getLockKey(String fullKey) {
        return fullKey + "_lock";
    }

    /**
     * 根据Key获取缓存信息
     *
     * @param key 不含cacheKeyPrefix的key
     * @return
     */
    @Override
    public T getCache(String appId, String key) {
        String valueStr = redisTemplate.boundValueOps(key).get();
        ParameterizedTypeImpl trueType = (ParameterizedTypeImpl) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Class<T> clazz = (Class<T>) trueType.getRawType();
        return StringUtils.isNotBlank(valueStr) ? JacksonParser.toObject(valueStr, clazz) : null;
    }

    /**
     * 获取App的缓存
     *
     * @param appId
     * @return {@link V}
     */
    public V getValByAppId(String appId) {
        return get(appId, getFullKey(TOKENS_CACHE_KEY_TAG, appId));
    }

    /**
     * 获取缓存信息,缓存不存在时,获取新内容
     *
     * @return
     */
    @Override
    public V get(String appId, String key) {
        return get(appId, key, false).getValue();
    }

    /**
     * 获取缓存信息
     *
     * @param key       含cachePrefixKey
     * @param isRefresh 是否刷新缓存
     * @return T <T>
     */
    @Override
    public T get(String appId, String key, Boolean isRefresh) {
        return super.get(appId, key, isRefresh);
    }

    @Override
    public RawLock getRawLock(String key) {
        final String lockKey = getLockKey(key);
        Boolean lock = RedisTemplateOperationsUtil.setIfAbsent(redisTemplate, lockKey, LOCK_VAL, rawLockWaitSeconds, TimeUnit.SECONDS);
        RawLock rawLock = new RawLock() {
            @Override
            public Boolean releaseLock() {
                redisTemplate.delete(lockKey);
                return Boolean.TRUE;
            }
        };
        rawLock.setLock(lock);
        return rawLock;
    }

    @Override
    public T saveCache(String appId, String key, T value, Long expiresSeconds) {
        BoundValueOperations redisKey = redisTemplate.boundValueOps(key);
        String valueStr = JacksonParser.toJsonString(value);
        if (expiresSeconds == null || expiresSeconds <= 0) {
            redisKey.set(valueStr);
        } else {
            redisKey.set(valueStr, expiresSeconds, TimeUnit.SECONDS);
        }
        return value;
    }

    @Override
    public T removeCache(String appId, String key) {
        key = getFullKey(key);
        T val = getCache(appId, key);
        if (val != null) {
            redisTemplate.delete(key);
        }
        return val;
    }

    /**
     * 获取缓存的完整key
     * 格式: {cacheKeyPrefix}:{key}
     *
     * @param key
     * @return
     */
    public String getFullKey(String key) {
        Objects.requireNonNull(key, "key must be not null");
        return cacheKeyPrefix != null ? (cacheKeyPrefix + ":" + key) : key;
    }

    /**
     * 获取缓存的完整key
     * 格式: {cacheKeyPrefix}:{key}
     *
     * @param key   不含前缀的key
     * @param appId appId
     * @return fullKey
     */
    public String getFullKey(String key, String appId) {
        Objects.requireNonNull(key, "key must be not null");
        key += appId;
        return getFullKey(key);
    }

    /**
     * 更具appId获取原始信息
     *
     * @param appId
     * @return
     */
    @Override
    public abstract T getRaw(String appId);

    @Override
    public T refreshCache(String appId, String key) {
        return get(appId, key, true);
    }

    /**
     * 通过默认key刷新缓存
     *
     * @return
     */
    public V refreshByAppId(String appId) {
        return refreshCache(appId, getFullKey(TOKENS_CACHE_KEY_TAG, appId)).getValue();
    }

    public String getCacheKeyPrefix() {
        return cacheKeyPrefix;
    }

    public void setCacheKeyPrefix(String cacheKeyPrefix) {
        this.cacheKeyPrefix = cacheKeyPrefix;
    }

    public Long getExpiresSeconds() {
        return expiresSeconds;
    }

    public void setExpiresSeconds(Long expiresSeconds) {
        this.expiresSeconds = expiresSeconds;
    }
}
