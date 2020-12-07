package org.kangspace.wechat.cache;

/**
 * <pre>
 * 微信相关缓存接口
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2020/11/4 11:06
 */
public interface WeChatCacheOperator<T,V>{
    /**
     * 获取数据值并缓存信息
     * @param key
     * @return
     */
    V get(String appId, String key);

    /**
     * 获取缓存信息
     * @param key
     * @return
     */
    T getCache(String appId, String key);

    /**
     * 获取原始数据
     * @param appId
     * @return
     */
    T getRaw(String appId);

    /**
     * 保存缓存信息
     * @param key
     * @param value
     * @param expiresSeconds 有效时间,单位s
     * @return
     */
    T saveCache(String appId, String key, T value, Long expiresSeconds);

    /**
     * 删除缓存
     * @param key
     * @return
     */
    T removeCache(String appId, String key);

    /**
     * 刷新缓存
     * @param key
     * @return
     */
    T refreshCache(String appId, String key);
}
