package org.kangspace.wechat.cache;


import org.kangspace.wechat.mp.AccessTokenReturnBean;
import org.kangspace.wechat.mp.MpInterfaceAccess;

import java.util.Objects;

/**
 * <pre>
 * 微信公众号AccessToken缓存类
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/11/4 11:08
 */
public class WeChatAccessTokenCache extends AbstractRedisWeChatCacheOperator<AbstractRedisWeChatCacheOperator.ExpireValue<String>,String> {
    /**
     * 缓存Key默认前缀
     */
    private static final String CACHE_KEY_DEFAULT_PREFIX = "wechat";

    private MpInterfaceAccess mpInterfaceAccess;

    @Override
    public ExpireValue<String> getRaw(String appId) {
        AccessTokenReturnBean bean = this.mpInterfaceAccess.getAccessToken(appId);
        Objects.requireNonNull(bean, "获取AccessToken错误,值为空");
        return new ExpireValue<>(bean.getAccessToken(), bean.getExpiresIn().longValue());
    }

    public WeChatAccessTokenCache(String cacheKeyPrefix, Long expiresSeconds) {
        this(cacheKeyPrefix, expiresSeconds, new MpInterfaceAccess());
    }

    public WeChatAccessTokenCache() {
        this(CACHE_KEY_DEFAULT_PREFIX, DEFAULT_EXPIRE_SECOND);
    }

    public WeChatAccessTokenCache(String cacheKeyPrefix, Long expiresSeconds,MpInterfaceAccess mpInterfaceAccess) {
        super();
        this.setCacheKeyPrefix(cacheKeyPrefix);
        this.setExpiresSeconds(expiresSeconds);
        this.mpInterfaceAccess = mpInterfaceAccess;
    }

    public MpInterfaceAccess getMpInterfaceAccess() {
        return mpInterfaceAccess;
    }

    public void setMpInterfaceAccess(MpInterfaceAccess mpInterfaceAccess) {
        this.mpInterfaceAccess = mpInterfaceAccess;
    }
}
