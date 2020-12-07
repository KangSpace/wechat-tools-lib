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
 * @date 2020/11/4 11:08
 */
public class WeChatAccessTokenCache extends AbstractRedisWeChatCacheOperator<AbstractRedisWeChatCacheOperator.ExpireValue<String>,String> {

    @Override
    public ExpireValue<String> getRaw(String appId) {
        AccessTokenReturnBean bean = new MpInterfaceAccess().getAccessToken(appId);
        Objects.requireNonNull(bean, "获取AccessToken错误,获取为空");
        return new ExpireValue<>(bean.getAccessToken(), bean.getExpiresIn().longValue());
    }

    public WeChatAccessTokenCache(String cacheKeyPrefix, Long expiresSeconds) {
        super();
        this.setCacheKeyPrefix(cacheKeyPrefix);
        this.setExpiresSeconds(expiresSeconds);
    }

    public WeChatAccessTokenCache() {
    }
}
