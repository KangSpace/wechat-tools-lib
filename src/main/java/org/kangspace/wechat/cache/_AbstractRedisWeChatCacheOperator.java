package org.kangspace.wechat.cache;

import org.apache.commons.lang.StringUtils;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.util.JacksonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 微信相关缓存抽象实现
 * 基于RedisTemplate缓存,需在Spring中注册redisTemplate
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2020/11/4 11:06
 */
@Deprecated
public abstract class _AbstractRedisWeChatCacheOperator<T extends AbstractWeChatCacheOperator.ExpireValue<V>, V> implements WeChatCacheOperator<T,V> {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * token缓存key字符串
     */
    public static String MP_TOKENS_CACHE_KEY = "mp_tokens:";
    /**
     * accessToken缓存key
     * 格式 {cacheKeyPrefix}{@value MP_TOKENS_CACHE_KEY}{AppID}
     */
    public static String ACCESS_TOKEN_CACHE_KEY = MP_TOKENS_CACHE_KEY + WeChatConfig.getDefaultAppIdSecret().getAppId();

    public static int TEN_SECONDS  = 10;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
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
    private static final String LOCK_VAL = "1";
    private final long LOCK_RECHECK_TIMEOUT = 1000L;
    /**
     * token过期空隙时间,如token有效期为10分钟,则设置缓存为 10*60 -{@value TOKEN_EXPIRE_GAP_SECOND_TIME} 分钟
     */
    private final static long TOKEN_EXPIRE_GAP_SECOND_TIME = 3L*60;

    private String getLockKey(String fullKey) {
        return fullKey + "_lock";
    }

    /**
     * 根据Key获取缓存信息
     *
     * @param key 不含cacheKeyPrefix的key
     * @return T
     */
    @Override
    public T getCache(String appId, String key) {
        String valueStr = redisTemplate.boundValueOps(key).get();
        ParameterizedTypeImpl trueType = (ParameterizedTypeImpl) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Class<T> clazz = (Class<T>) trueType.getRawType();
        return StringUtils.isNotBlank(valueStr) ? JacksonParser.toObject(valueStr, clazz) : null;
    }

    /**
     * 获取默认缓存具体值
     *
     * @return V
     */
    public V getValByDefaultKey() {
        return get(WeChatConfig.getDefaultAppIdSecret().getAppId(),ACCESS_TOKEN_CACHE_KEY);
    }
    /**
     * 获取App的缓存
     * @param appId
     * @return {@link V}
     */
    public V getValByAppId(String appId) {
        return get(appId,MP_TOKENS_CACHE_KEY+appId);
    }

    /**
     * 获取缓存信息,缓存不存在时,获取新内容
     *
     * @return V
     */
    @Override
    public V get(String appId,String key) {
        return get(appId,key, false).getValue();
    }

    /**
     * 获取缓存信息
     * @param key 不含cachePrefixKey
     * @param isRefresh 是否刷新缓存
     * @return T
     */
    public T get(String appId,String key,Boolean isRefresh) {
        key = getFullKey(key);
        //获取缓存
        T cache = getCache(appId,key);
        if (cache == null || isRefresh) {
            int waitCount = 0;
            String lockKey = getLockKey(key);
            try {
                //token申请时,做10s分布式锁,获取不到锁时,最多等待10s
                while (!RedisTemplateOperationsUtil.setIfAbsent(redisTemplate, lockKey, LOCK_VAL, TEN_SECONDS, TimeUnit.SECONDS)) {
                    log.debug("get(String key),key:{} [not get the lock] ,waiting the {} time(s)", lockKey, waitCount + 1);
                    if (waitCount >= TEN_SECONDS*1000/LOCK_RECHECK_TIMEOUT) {
                        break;
                    }
                    try {
                        Thread.sleep(LOCK_RECHECK_TIMEOUT);
                    } catch (InterruptedException e) {
                    }
                    waitCount++;
                }
                //等待后拿到锁时,重新获取缓存,缓存不存在时继续获取数据
                if (waitCount > 0) {
                    cache = getCache(appId,key);
                    if (cache != null) {
                        log.debug("get(String key),key:{} [re-get the lock ,and found cache]:{}", lockKey, cache);
                        return cache;
                    }
                }
                log.debug("get(String key),key:{} ,lockKey:{}[get the lock ,and get new value]", key,lockKey);
                cache = getRaw(appId);
                if (cache != null) {
                    Long expireSeconds = cache.getExpiresSeconds() != null ?
                            (cache.getExpiresSeconds() > TOKEN_EXPIRE_GAP_SECOND_TIME ? cache.getExpiresSeconds() - TOKEN_EXPIRE_GAP_SECOND_TIME : cache.getExpiresSeconds())
                            : null;
                    if (expireSeconds == null || getExpiresSeconds() != null) {
                        expireSeconds = expiresSeconds;
                    }
                    saveCache(appId,key, cache, expireSeconds);
                    log.debug("get(String key),key:{}  [got the lock ,and refreshed cache]:{}", key, cache);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw e;
            } finally {
                redisTemplate.delete(lockKey);
            }
        }
        return cache;
    }

    @Override
    public T saveCache(String appId,String key, T value, Long expiresSeconds) {
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
    public T removeCache(String appId,String key) {
        key = getFullKey(key);
        T val = getCache(appId,key);
        if (val != null) {
            redisTemplate.delete(key);
        }
        return val;
    }

    /**
     * 获取缓存的完整key
     * 格式: {cacheKeyPrefix}:{key}
     * @param key
     * @return String
     */
    public String getFullKey(String key) {
        Objects.requireNonNull(key, "key must be not null");
        return cacheKeyPrefix != null ? (cacheKeyPrefix +":"+ key) : key;
    }

    /**
     * 更具appId获取原始信息
     *
     * @return T
     */
    @Override
    public abstract T getRaw(String appId);

    @Override
    public T refreshCache(String appId,String key) {
        return get(appId,key,true);
    }

    /**
     * 通过默认key刷新缓存
     * @return V
     */
    public V refreshByDefaultKey() {
        return refreshByAppId(WeChatConfig.getDefaultAppIdSecret().getAppId());
    }
    /**
     * 通过默认key刷新缓存
     * @return V
     */
    public V refreshByAppId(String appId) {
        return refreshCache(appId,MP_TOKENS_CACHE_KEY+appId).getValue();
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
