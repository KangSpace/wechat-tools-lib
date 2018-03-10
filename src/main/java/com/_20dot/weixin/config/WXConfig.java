package com._20dot.weixin.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 微信配置常量类
 * @date 2017/2/10 17:31
 */
public class WXConfig {
    private static Logger logger = Logger.getLogger(WXConfig.class.getName());


    /**
     * 获取网页授权URL
     * redirect将被URLEncode
     * @param redirectUri 回调路径
     * @param scope 授权作用域 {@link OAth2Scope}
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:36
     * @return
     */
    public static String getOauth2AuthorizeUrl(String redirectUri,OAth2Scope scope,String state) throws UnsupportedEncodingException {
        if(state == null)
            state = "";
        if(redirectUri!=null)
            redirectUri = URLEncoder.encode(redirectUri,"UTF-8");
        return _replaceAppId(OAUTH2_AUTHORIZE_URL).replace("REDIRECT_URI",redirectUri).replace("SCOPE",scope.toString()).replace("STATE",state);
    }

    /**
     * 获取网页授权AccessToken URL
     * @param
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:42
     * @return
     */
    public static String getOauth2AccessTokenUrl(String code){
        return _replaceAppIdAndAppSecret(OAUTH2_ACCESS_TOKEN_URL).replace("CODE",code);
    }

    /**
     * 获取公众号 AccessToken
     * @param
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:47
     * @return
     */
    public static String getMpAccesstokenUrl() {
        return _replaceAppIdAndAppSecret(MP_ACCESSTOKEN_URL);
    }

    /**
     * 获取公众号 获取用户基本信息
     * @param accessToken
     * @param openId 用户openId
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:48
     * @return
     */
    public static String getMpUserInfo(String accessToken ,String openId) {
        return _replaceAccessToken(MP_USER_INFO_URL,accessToken).replace("OPENID",openId);
    }

    /**
     * 获取发送模板消息接口 URL
     * @param accessToken
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:50
     * @return
     */
    public static String getMpMessageTemplateSendUrl(String accessToken) {
        return _replaceAccessToken(MP_MESSAGE_TEMPLATE_SEND_URL,accessToken);
    }

    /**
     * 获取公众号jssdk jsApiTcket接口 URL
     * @param accessToken
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:53
     * @return
     */
    public static String getMpJsSdkTicketUrl(String accessToken) {
        return _replaceAccessToken(MP_JS_SDK_TICKET_URL,accessToken);
    }

    /**
     * 获取网页授权用户信息URL
     * @param accessToken
     * @param openId
     * @Author kango2gler@gmail.com
     * @Date 2017/6/6 16:25
     * @return
     */
    public static String getOAth2UserInfo(String accessToken, String openId,String lang) {
        return _replaceAccessToken(OAUTH2_USER_INFO_URL,accessToken).replace("OPENID",openId).replace("LANG",lang);
    }


    /*******************************************************************************************/

    /**
     * 替换 accessToken
     * @param _str 需要替换的字符串
     * @param accessToken
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:51
     * @return
     */
    private static String _replaceAccessToken(String _str,String accessToken){
        return _str.replace("ACCESS_TOKEN",accessToken);
    }

    /**
     * 替换 appid 和 appsecret
     * @param _str
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:34
     * @return
     */
    private static String _replaceAppIdAndAppSecret(String _str){
        return _replaceAppSecret(_replaceAppId(_str));
    }
    /**
     * 替换 appid
     * @param _str
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:34
     * @return
     */
    private static String _replaceAppId(String _str){
        return _str.replace("APPID",APP_ID);
    }
    /**
     * 替换 appsecret
     * @param _str
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:34
     * @return
     */
    private static String _replaceAppSecret(String _str){
        return _str.replace("APPSECRET",APP_SECRET);
    }


    /**
     * APPID,从配置文件中获取
     */
    public static String APP_ID = "APP_ID";
    /**
     * APP_SECRET,从配置文件中获取
     */
    public static String APP_SECRET = "APP_SECRET";
    /**
     * OAUTH2_AUTHORIZE_URL,
     * 网页授权接口请求链接,从配置文件中获取
     */
    public static String OAUTH2_AUTHORIZE_URL = "OAUTH2_AUTHORIZE_URL";
    /**
     * OAUTH2_ACCESS_TOKEN_URL,
     * 网页授权获取ACCESS_TOKEN接口请求链接,从配置文件中获取
     */
    public static String OAUTH2_ACCESS_TOKEN_URL = "OAUTH2_ACCESS_TOKEN_URL";
    /**
     * OAUTH2_USER_INFO_URL,
     * 网页授权获取用户信息接口请求链接,从配置文件中获取
     */
    public static String OAUTH2_USER_INFO_URL = "OAUTH2_USER_INFO_URL";
    /**
     * MP_ACCESSTOKEN_URL,
     * 公众号ACCESS_TOKEN接口请求链接,从配置文件中获取
     */
    public static String MP_ACCESSTOKEN_URL = "MP_ACCESSTOKEN_URL";
    /**
     * MP_USER_INFO_URL,
     * 获取用户基本信息(UnionID机制)接口请求链接,从配置文件中获取
     */
    public static String MP_USER_INFO_URL = "MP_USER_INFO_URL";
    /**
     * MP_MESSAGE_TEMPLATE_SEND_URL,
     * 发送模板消息接口接口请求链接,从配置文件中获取
     */
    public static String MP_MESSAGE_TEMPLATE_SEND_URL = "MP_MESSAGE_TEMPLATE_SEND_URL";
    /**
     * MP_JS_SDK_TICKET_URL,
     * 公众号jssdk jsApiTcket获取接口接口请求链接,从配置文件中获取
     */
    public static String MP_JS_SDK_TICKET_URL = "MP_JS_SDK_TICKET_URL";
    /**
     * 模版id,目前为单个
     */
    public static String MP_MESSAGE_TEMPLATE_IDS = "MP_MESSAGE_TEMPLATE_IDS";
    static{
        APP_ID = WXConfigHelper.getValue("APP_ID");
        APP_SECRET = WXConfigHelper.getValue("APP_SECRET");
        OAUTH2_AUTHORIZE_URL = WXConfigHelper.getValue("OAUTH2_AUTHORIZE_URL");
        OAUTH2_ACCESS_TOKEN_URL = WXConfigHelper.getValue("OAUTH2_ACCESS_TOKEN_URL");
        OAUTH2_USER_INFO_URL = WXConfigHelper.getValue("OAUTH2_USER_INFO_URL");
        MP_ACCESSTOKEN_URL = WXConfigHelper.getValue("MP_ACCESSTOKEN_URL");
        MP_USER_INFO_URL = WXConfigHelper.getValue("MP_USER_INFO_URL");
        MP_MESSAGE_TEMPLATE_SEND_URL = WXConfigHelper.getValue("MP_MESSAGE_TEMPLATE_SEND_URL");
        MP_JS_SDK_TICKET_URL = WXConfigHelper.getValue("MP_JS_SDK_TICKET_URL");
        MP_MESSAGE_TEMPLATE_IDS = WXConfigHelper.getValue("MP_MESSAGE_TEMPLATE_IDS");

        logger.info("initial wx-config: APP_ID:"+APP_ID+"\n"+
                        "APP_SECRET:"+APP_SECRET+"\n"+
                "OAUTH2_AUTHORIZE_URL:"+OAUTH2_AUTHORIZE_URL+"\n"+
                "OAUTH2_ACCESS_TOKEN_URL:"+OAUTH2_ACCESS_TOKEN_URL+"\n"+
                "OAUTH2_USER_INFO_URL:"+OAUTH2_USER_INFO_URL+"\n"+
                "MP_ACCESSTOKEN_URL:"+MP_ACCESSTOKEN_URL+"\n"+
                "MP_USER_INFO_URL:"+MP_USER_INFO_URL+"\n"+
                "MP_MESSAGE_TEMPLATE_SEND_URL:"+MP_MESSAGE_TEMPLATE_SEND_URL+"\n"+
                "MP_JS_SDK_TICKET_URL:"+MP_JS_SDK_TICKET_URL+"\n"+
                "MP_MESSAGE_TEMPLATE_IDS:"+MP_MESSAGE_TEMPLATE_IDS+"\n");

    }


    /**
     * 网页授权 作用域
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 16:37
     */
    public enum OAth2Scope{
        /**
         * snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
         * @Author kango2gler@gmail.com
         * @Date 2017/2/13 16:38
         */
        SNSAPI_BASE{
            @Override
            public String toString() {
                return super.toString().toLowerCase();
            }
        },
        /**
         * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
         * @Author kango2gler@gmail.com
         * @Date 2017/2/13 16:38
         */
        SNSAPI_USERINFO{
            @Override
            public String toString() {
                return super.toString().toLowerCase();
            }
        }
    }
}
