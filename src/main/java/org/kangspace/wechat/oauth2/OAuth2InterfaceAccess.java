package org.kangspace.wechat.oauth2;

import org.kangspace.wechat.bean.Lang;
import org.kangspace.wechat.bean.WeChatReturnBean;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.util.WeChatUtil;
import org.kangspace.wechat.util.WebUtil;
import org.kangspace.wechat.util.http.MyAbstractHttp;
import org.kangspace.wechat.util.http.MyHttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 *  网页授权访问类
 * @since 2017/2/15 17:22
 */
public class OAuth2InterfaceAccess {
    private static Logger logger = Logger.getLogger(OAuth2InterfaceAccess.class.getName());

    /**
     * 302重定向跳转到网页授权
     *
     * @param redirectURI 网页授权回调地址
     * @param param       网页授权state参数设置
     * @param request
     * @param response
     * @author kango2gler@gmail.com
     * @since 2017/2/15 17:24
     */
    public void redirectToAuthorizeUrl(String redirectURI, String param, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        redirectToAuthorizeUrl(WeChatConfig.getDefaultAppIdSecret().getAppId(), redirectURI, param, WeChatConfig.OAth2Scope.SNSAPI_BASE, request, response);
    }

    /**
     * 302重定向跳转到网页授权
     *
     * @param appId       微信APP_ID
     * @param redirectURI 网页授权回调地址
     * @param param       网页授权state参数设置
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    public void redirectToAuthorizeUrl(String appId, String redirectURI, String param, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        redirectToAuthorizeUrl(appId, redirectURI, param, WeChatConfig.OAth2Scope.SNSAPI_BASE, request, response);
    }

    /**
     * 302重定向跳转到网页授权
     *
     * @param redirectURI 网页授权回调地址
     * @param param       网页授权state参数设置
     * @param scope       授权雷鑫
     * @param request     request
     * @param response    response
     * @author kango2gler@gmail.com
     * @since 2017/2/15 17:24
     */
    public void redirectToAuthorizeUrl(String appId, String redirectURI, String param, WeChatConfig.OAth2Scope scope, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String url = WeChatConfig.getOauth2AuthorizeUrl(appId, redirectURI, scope, (param != null ? URLEncoder.encode(param, "UTF-8") : ""));
        WebUtil.redirectRequest(url, request, response);
    }

    /**
     * 获取网页授权AccessToken
     *
     * @param code 网页授权返回的code
     * @return OAuth2AccessTokenReturnBean
     * @author kango2gler@gmail.com
     * @since 2017/2/15 17:36
     */
    public OAuth2AccessTokenReturnBean getAccessToken(String code) {
        return getAccessToken(WeChatConfig.getDefaultAppIdSecret().getAppId(), code);
    }

    public OAuth2AccessTokenReturnBean getAccessToken(String appId, String code) {
        String url = WeChatConfig.getOauth2AccessTokenUrl(appId, code);
        MyAbstractHttp client = MyHttpUtil.getClient(url);
        OAuth2AccessTokenReturnBean rb = WeChatUtil.asReturnBean(client.get(), OAuth2AccessTokenReturnBean.class);
        return rb;
    }

    /**
     * 通过code获取openId
     *
     * @param code code
     * @return String
     */
    public String getOpenId(String code) {
        return getOpenId(WeChatConfig.getDefaultAppIdSecret().getAppId(), code);
    }

    /**
     * 通过code获取openId
     *
     * @param appId appId
     * @param code code
     * @return String
     */
    public String getOpenId(String appId, String code) {
        OAuth2AccessTokenReturnBean bean = getAccessToken(appId, code);
        if (WeChatReturnBean.isSuccess(bean)) {
            return bean.getOpenid();
        } else {
            logger.severe("获取网页授权AccessToken失败,error:" + bean);
        }
        return null;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param accessToken accessToken
     * @param openId openId
     * @param lang lang
     * @return OAuth2UserInfoReturnBean
     * @author kango2gler@gmail.com
     * @since 2017/6/6 16:15
     */
    public OAuth2UserInfoReturnBean getUserInfo(String accessToken, String openId, Lang lang) {
        String url = WeChatConfig.getOAth2UserInfoUrl(accessToken, openId, lang.toString());
        MyAbstractHttp client = MyHttpUtil.getClient(url);
        return WeChatUtil.asReturnBean(client.get(), OAuth2UserInfoReturnBean.class);
    }

    /**
     * 获取用户头像和昵称
     *
     * @param accessToken accessToken
     * @param openId openId
     * @return Map
     * @author kango2gler@gmail.com
     * @since 2018/2/8 10:20
     */
    public Map<String, String> getUserNickNameAndHeadImg(String accessToken, String openId) {
        OAuth2UserInfoReturnBean bean = getUserInfo(accessToken, openId, Lang.ZH_CN);
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("nickName", bean.getNickname());
        returnMap.put("headImgUrl", bean.getHeadimgurl());
        return returnMap;
    }
}
