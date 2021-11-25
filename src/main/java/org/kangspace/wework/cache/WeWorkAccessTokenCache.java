package org.kangspace.wework.cache;


import org.kangspace.wechat.cache.AbstractRedisWeChatCacheOperator;
import org.kangspace.wechat.mp.AccessTokenReturnBean;
import org.kangspace.wework.WeWorkInterfaceAccess;

import java.util.Objects;

/**
 * <pre>
 * 企业微信AccessToken缓存类
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2021/08/28 16:16:32
 */
public class WeWorkAccessTokenCache extends AbstractRedisWeChatCacheOperator<AbstractRedisWeChatCacheOperator.ExpireValue<String>, String> {
    private WeWorkInterfaceAccess weWorkInterfaceAccess;

    public WeWorkAccessTokenCache(String cacheKeyPrefix, Long expiresSeconds) {
        super();
        this.setCacheKeyPrefix(cacheKeyPrefix);
        this.setExpiresSeconds(expiresSeconds);
        weWorkInterfaceAccess = new WeWorkInterfaceAccess();
    }

    public WeWorkAccessTokenCache() {
        this(null, DEFAULT_EXPIRE_SECOND);
    }

    @Override
    public ExpireValue<String> getRaw(String appId) {
        AccessTokenReturnBean bean = weWorkInterfaceAccess.getAccessToken(appId);
        Objects.requireNonNull(bean, "获取AccessToken错误,获取为空");
        return new ExpireValue<>(bean.getAccessToken(), bean.getExpiresIn().longValue());
    }
}
