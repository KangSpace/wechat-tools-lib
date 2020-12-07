package org.kangspace.wechat.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * <pre>
 * 抽象微信缓存处理类
 * </pre>
 *
 * @author kangxuefeng@etiantian.com
 * @date 2020/12/7 13:52
 */
public abstract class AbstractWeChatCacheOperator<T extends AbstractWeChatCacheOperator.ExpireValue<V>, V> implements WeChatCacheOperator<T,V> {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 获取原始数据时的分布式锁等待超时时间,默认10s
     */
    private Long rawlockWaitMilliseconds = 10_1000L;
    /**
     * 默认10ms
     */
    private Long rawlockRecheckMilliseconds = 10L;

    /**
     * <pre>
     *  获取缓存信息,缓存失效时,重新刷新缓存
     *  </pre>
     * @param appId 获取缓存的的appId
     * @param key 缓存key
     * @param isRefresh 是否刷新缓存
     * @return
     */
    public T get(String appId, String key,Boolean isRefresh) {
        T cache = getCache(appId, key);
        if (cache == null || isRefresh) {
            int waitCount = 0;
            RawLock rawLock= getRawLock(key);
            if (rawLock == null) {
                throw new NullPointerException("rawLock mush be not null");
            }
            try {
                //token申请时,做10s分布式锁,获取不到锁时,最多等待10s
                while (!rawLock.getLock()) {
                    log.debug("get(String appId,String key,Boolean isRefresh),appId:{} data key:{} [not get the lock] ,waiting the {} time(s)",appId, key, waitCount + 1);
                    if (waitCount >= rawlockWaitMilliseconds/rawlockRecheckMilliseconds) {
                        break;
                    }
                    try {
                        Thread.sleep(rawlockRecheckMilliseconds);
                    } catch (InterruptedException e) {
                    }
                    waitCount++;
                }
                //等待后拿到锁时,重新获取缓存,缓存不存在时继续获取数据
                if (waitCount > 0) {
                    cache = getCache(appId,key);
                    if (cache != null) {
                        log.debug("get(String appId,String key,Boolean isRefresh),appId:{} data key:{} [re-get the lock ,and found cache]:",appId, key, cache);
                        return cache;
                    }
                }
                log.debug("get(String appId,String key,Boolean isRefresh),appId:{}key:{} [get the lock ,and get new value]",appId, key);
                cache = getRaw(appId);
                if (cache != null) {
                    cache = saveCache(appId, key, cache, cache.getExpiresSeconds());
                    log.debug("get(String appId,String key,Boolean isRefresh),appId:{},key:{}  [got the lock ,and refreshed cache]:{}",appId, key, cache);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw e;
            } finally {
                if (rawLock.getLock()) {
                    rawLock.releaseLock();
                }
            }
        }
        return cache;
    }

    @Override
    public V get(String appId, String key) {
        return get(appId, key, Boolean.FALSE).getValue();
    }
    /**
     * 通过默认key刷新缓存
     * @return
     */
    public V refresh(String appId,String key) {
        return refreshCache(appId,key).getValue();
    }

    /**
     * <pre>
     * 获取原始数据时的分布式锁,只有获取到锁的记录更新数据;
     * 该锁应该为带超时时间的分布式锁
     * </pre>
     * @return
     */
    public abstract RawLock getRawLock(String key);

    @Override
    public abstract T getCache(String appId, String key);

    @Override
    public abstract T getRaw(String appId);

    @Override
    public abstract T saveCache(String appId, String key, T value, Long expiresSeconds);

    @Override
    public abstract T removeCache(String appId, String key);

    @Override
    public T refreshCache(String appId, String key) {
        return get(appId, key, Boolean.TRUE);
    }

    /**
     * 带超时时间的值
     *
     * @param <T>
     */
    public static class ExpireValue<T> {
        private T value;
        private Long expiresSeconds;
        private Date cTime;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Long getExpiresSeconds() {
            return expiresSeconds;
        }

        public void setExpiresSeconds(Long expiresSeconds) {
            this.expiresSeconds = expiresSeconds;
        }

        public Date getcTime() {
            return cTime;
        }

        public void setcTime(Date cTime) {
            this.cTime = cTime;
        }

        public ExpireValue() {
        }

        public ExpireValue(T value, Long expiresSeconds) {
            this.value = value;
            this.expiresSeconds = expiresSeconds;
            this.cTime = new Date();
        }

        @Override
        public String toString() {
            return "ExpireValue{" +
                    "value=" + value +
                    ", expiresSeconds=" + expiresSeconds +
                    ", cTime=" + cTime +
                    '}';
        }
    }

    /**
     * <pre>
     * 获取原数据时的锁对象,
     * 用于提供锁结果及解锁操作
     * </pre>
     */
    public static abstract class RawLock{
        /**
         * 是否获取到锁
         */
        private Boolean lock;

        /**
         * 解锁
         * @return
         */
        public abstract Boolean releaseLock();

        public Boolean getLock() {
            return lock;
        }

        public void setLock(Boolean lock) {
            this.lock = lock;
        }

        @Override
        public String toString() {
            return "RawLock{" +
                    "lock=" + lock +
                    '}';
        }
    }
}
