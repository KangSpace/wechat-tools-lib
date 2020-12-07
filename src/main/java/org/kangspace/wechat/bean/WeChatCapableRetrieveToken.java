package org.kangspace.wechat.bean;


import org.kangspace.wechat.util.jdk18.Function;

/**
 * <pre>
 * 重新获取token接口
 * 提供token过期重新获取token方法
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2020/12/4 11:07
 */
public abstract class WeChatCapableRetrieveToken {

    /**
     * token过期时,重新获取token再次发次请求方法
     * @param appId 当前请求的APP_ID
     * @param returnBean 请求微信返回的ReturnBean
     * @param retrieveTokenFn input:appId, return: newAccessToken
     * @param retry input: accessToken, return T
     * @return
     */
    public <T> T retrieveTokenRetry(String appId, WeChatReturnBean returnBean, Function<String, String> retrieveTokenFn, Function<String, T> retry) {
        if (!WeChatReturnBean.isSuccess(returnBean) && WeChatReturnBean.isAccessTokenInvalid(returnBean)) {
            String newAccessToken = retrieveTokenFn.apply(appId);
            return retry.apply(newAccessToken);
        }
      return null;
    }
}
