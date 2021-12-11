package org.kangspace.wechat.cache;

import org.kangspace.wechat.mp.AccessTokenReturnBean;
import org.kangspace.wechat.mp.JSApiTicketReturnBean;
import org.kangspace.wechat.mp.MpInterfaceAccess;

import java.util.Objects;

/**
 * <pre>
 * 微信公众号JsApiTicket缓存类
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/11/4 11:08
 */
public class WeChatJsApiTicketCache extends AbstractRedisWeChatCacheOperator<AbstractRedisWeChatCacheOperator.ExpireValue<String>,String> {
    private final static String JS_API_TICKET_CACHE_KEY_PREFIX = "mp_js_api_ticket";
    @Override
    public ExpireValue<String> getRaw(String appId) {
        MpInterfaceAccess mpInterfaceAccess = new MpInterfaceAccess();
        AccessTokenReturnBean bean = mpInterfaceAccess.getAccessToken(appId);
        Objects.requireNonNull(bean, "获取AccessToken错误,获取为空");
        JSApiTicketReturnBean jsApiTicket = mpInterfaceAccess.getJsApiTicket(bean.getAccessToken());
        return new ExpireValue<>(jsApiTicket.getTicket(), jsApiTicket.getExpiresIn().longValue());
    }

    public WeChatJsApiTicketCache(String cacheKeyPrefix, Long expiresSeconds) {
        super();
        this.setCacheKeyPrefix(cacheKeyPrefix!=null?cacheKeyPrefix+JS_API_TICKET_CACHE_KEY_PREFIX:JS_API_TICKET_CACHE_KEY_PREFIX);
        this.setExpiresSeconds(expiresSeconds);
    }

    public WeChatJsApiTicketCache() {}
}
