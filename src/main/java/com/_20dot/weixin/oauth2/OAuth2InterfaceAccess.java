package com._20dot.weixin.oauth2;

import com._20dot.weixin.config.WXConfig;
import com._20dot.weixin.util.WebUtil;
import com._20dot.weixin.util.http.MyAbstractHttp;
import com._20dot.weixin.util.http.MyHttpUtil;
import com._20dot.weixin.util.WXUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kango2gler@gmail.com
 * @desc 网页授权访问类
 * @date 2017/2/15 17:22
 */
public class OAuth2InterfaceAccess {

    /**
     * 302重定向跳转到网页授权
     *
     * @param redirectURI 网页授权回调地址
     * @param param       网页授权state参数设置
     * @param request
     * @param response
     * @return
     * @Author kango2gler@gmail.com
     * @Date 2017/2/15 17:24
     */
    public void redirectToAuthorizeUrl(String redirectURI, String param, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        redirectToAuthorizeUrl(redirectURI, param, WXConfig.OAth2Scope.SNSAPI_BASE, request, response);
    }

    /**
     * 302重定向跳转到网页授权
     *
     * @param redirectURI 网页授权回调地址
     * @param param       网页授权state参数设置
     * @param scope       授权雷鑫
     * @param request
     * @param response
     * @return
     * @Author kango2gler@gmail.com
     * @Date 2017/2/15 17:24
     */
    public void redirectToAuthorizeUrl(String redirectURI, String param, WXConfig.OAth2Scope scope, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String url = WXConfig.getOauth2AuthorizeUrl(redirectURI, scope, (param != null ? URLEncoder.encode(param, "UTF-8") : ""));
        WebUtil.redirectRequest(url, request, response);
    }

    /**
     * 获取网页授权AccessToken
     *
     * @param code 网页授权返回的code
     * @return
     * @Author kango2gler@gmail.com
     * @Date 2017/2/15 17:36
     */
    public OAuth2AccessTokenReturnBean getAccessToken(String code) {
        String url = WXConfig.getOauth2AccessTokenUrl(code);
        MyAbstractHttp client = MyHttpUtil.getClient(url);
        OAuth2AccessTokenReturnBean rb = WXUtil.asReturnBean(client.get(), OAuth2AccessTokenReturnBean.class);
        return rb;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * @param accessToken
     * @param openId
     * @param lang
     * @Author kango2gler@gmail.com
     * @Date 2017/6/6 16:15
     * @return
     */
    public OAuth2UserInfoReturnBean getUserInfo(String accessToken,String openId,Lang lang){
        String url = WXConfig.getOAth2UserInfo(accessToken,openId,lang.toString());
        MyAbstractHttp client = MyHttpUtil.getClient(url);
        return WXUtil.asReturnBean(client.get(), OAuth2UserInfoReturnBean.class);
    }

    /**
     * 获取用户头像和昵称
     * @param accessToken
     * @param openId
     * @author kango2gler@gmail.com
     * @date 2018/2/8 10:20
     * @return
     */
    public Map<String,String> getUserNickNameAndHeadImg(String accessToken, String openId){
        OAuth2UserInfoReturnBean bean = getUserInfo(accessToken,openId,Lang.ZH_CN);
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("nickName",bean.getNickname());
        returnMap.put("headImgUrl",bean.getHeadimgurl());
        return returnMap;
    }
}
