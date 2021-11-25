package org.kangspace.wework.cache;

import org.kangspace.wechat.cache.AbstractRedisWeChatCacheOperator;
import org.kangspace.wechat.mp.AccessTokenReturnBean;
import org.kangspace.wechat.mp.JSApiTicketReturnBean;
import org.kangspace.wework.WeWorkInterfaceAccess;

import java.util.Objects;

/**
 * <pre>
 * 企业微信应用JsApiTicket缓存类
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2021/11/24 23:19:47
 */
public class WeWorkAppJsApiTicketCache extends AbstractRedisWeChatCacheOperator<AbstractRedisWeChatCacheOperator.ExpireValue<String>, String> {
    private final static String JS_API_TICKET_CACHE_KEY_PREFIX = "wework_app_js_api_ticket";
    private WeWorkAccessTokenCache weWorkAccessTokenCache;
    private WeWorkInterfaceAccess weWorkInterfaceAccess;

    public WeWorkAppJsApiTicketCache(String cacheKeyPrefix, Long expiresSeconds) {
        this(cacheKeyPrefix, expiresSeconds, null);
    }

    public WeWorkAppJsApiTicketCache(String cacheKeyPrefix, Long expiresSeconds, WeWorkAccessTokenCache weWorkAccessTokenCache) {
        this(cacheKeyPrefix, expiresSeconds, weWorkAccessTokenCache, new WeWorkInterfaceAccess());
    }

    public WeWorkAppJsApiTicketCache(String cacheKeyPrefix, Long expiresSeconds, WeWorkAccessTokenCache weWorkAccessTokenCache,
                                     WeWorkInterfaceAccess weWorkInterfaceAccess) {
        super();
        this.setCacheKeyPrefix(cacheKeyPrefix != null ? cacheKeyPrefix + JS_API_TICKET_CACHE_KEY_PREFIX : JS_API_TICKET_CACHE_KEY_PREFIX);
        this.setExpiresSeconds(expiresSeconds);
        this.weWorkAccessTokenCache = weWorkAccessTokenCache;
        this.weWorkInterfaceAccess = weWorkInterfaceAccess;
    }

    public WeWorkAppJsApiTicketCache(WeWorkAccessTokenCache weWorkAccessTokenCache) {
        this(null, DEFAULT_EXPIRE_SECOND, weWorkAccessTokenCache);
    }

    public WeWorkAppJsApiTicketCache() {
        this(null, DEFAULT_EXPIRE_SECOND);
    }

    @Override
    public ExpireValue<String> getRaw(String appId) {
        String accessToken = getAccessToken(appId);
        JSApiTicketReturnBean jsApiTicket = weWorkInterfaceAccess.getAgentConfigTicket(accessToken);
        return new ExpireValue<>(jsApiTicket.getTicket(), jsApiTicket.getExpiresIn().longValue());
    }

    /**
     * 获取AccessToken
     *
     * @param appId
     * @return
     */
    private String getAccessToken(String appId) {
        if (this.weWorkAccessTokenCache != null) {
            return this.weWorkAccessTokenCache.getValByAppId(appId);
        }
        AccessTokenReturnBean bean = this.weWorkInterfaceAccess.getAccessToken(appId);
        Objects.requireNonNull(bean, "获取AccessToken错误,获取为空");
        return bean.getAccessToken();
    }
}
