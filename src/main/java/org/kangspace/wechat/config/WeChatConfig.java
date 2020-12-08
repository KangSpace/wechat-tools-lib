package org.kangspace.wechat.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.core.internal.Function;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 微信配置常量类
 * @date 2017/2/10 17:31
 */
public class WeChatConfig {
    private static Logger logger = Logger.getLogger(WeChatConfig.class.getName());


    /**
     * 获取网页授权URL,默认微信APP_ID
     * redirect将被URLEncode
     * @param redirectUri 回调路径
     * @param scope 授权作用域 {@link OAth2Scope}
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:36
     * @return
     * @see WeChatConfig#getOauth2AuthorizeUrl(String, String, OAth2Scope, String)
     */
    public static String getOauth2AuthorizeUrl(String redirectUri, OAth2Scope scope, String state) throws UnsupportedEncodingException {
        return getOauth2AuthorizeUrl(getDefaultAppIdSecret().getAppId(), redirectUri, scope, state);
    }

    /**
     * 获取网页授权URL
     * redirect将被URLEncode
     * @param appId 微信APP_ID
     * @param redirectUri 回调路径
     * @param scope 授权作用域 {@link OAth2Scope}
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return
     * @throws UnsupportedEncodingException
     * @see #getOauth2AuthorizeUrl(String, OAth2Scope, String)
     */
    public static String getOauth2AuthorizeUrl(String appId,String redirectUri, OAth2Scope scope, String state) throws UnsupportedEncodingException {
        if(state == null) {
            state = "";
        }
        if(redirectUri!=null) {
            redirectUri = URLEncoder.encode(redirectUri,"UTF-8");
        }
        return _replaceAppId(OAUTH2_AUTHORIZE_URL,appId).replace("REDIRECT_URI",redirectUri).replace("SCOPE",scope.toString()).replace("STATE",state);
    }

    /**
     * 获取网页授权AccessToken URL
     * @param code 网页授权返回的code
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:42
     * @return
     */
    public static String getOauth2AccessTokenUrl(String code){
        return getOauth2AccessTokenUrl(getDefaultAppIdSecret().getAppId(),code);
    }

    /**
     * 获取网页授权AccessToken URL
     * @param appId 微信APP_ID
     * @param code 网页授权返回的code
     * @return
     * @see #getOauth2AccessTokenUrl(String)
     */
    public static String getOauth2AccessTokenUrl(String appId,String code){
        String appSecret = getAppIdSecret(appId).getAppSecret();
        return _replaceAppIdAndAppSecret(OAUTH2_ACCESS_TOKEN_URL,appId,appSecret).replace("CODE",code);
    }

    /**
     * 获取公众号 AccessToken
     * @param
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:47
     * @return
     */
    public static String getMpAccesstokenUrl() {
        return _replaceAppIdAndAppSecret(MP_ACCESS_TOKEN_URL);
    }
    /**
     * 获取公众号 AccessToken
     * @param
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:47
     * @return
     */
    public static String getMpAccessTokenUrl(String appId) {
        String appSecret = getAppIdSecret(appId).getAppSecret();
        return _replaceAppIdAndAppSecret(MP_ACCESS_TOKEN_URL,appId,appSecret);
    }

    /**
     * 获取公众号 获取用户基本信息
     * @param accessToken
     * @param openId 用户openId
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:48
     * @return
     */
    public static String getMpUserInfo(String accessToken , String openId) {
        return _replaceAccessToken(MP_USER_INFO_URL,accessToken).replace("OPENID",openId);
    }
    /**
     * 获取公众号 批量获取用户基本信息
     * @param accessToken
     * @author kango2gler@gmail.com
     * @date 2020/11/09 12:04
     * @return
     */
    public static String getMpUserInfoBatch(String accessToken) {
        return _replaceAccessToken(MP_USER_INFO_BATCH_URL,accessToken);
    }

    /**
     * 获取公众号 获取关注者列表
     * @param accessToken
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @author kango2gler@gmail.com
     * @date 2020/11/09 11:42
     * @return
     */
    public static String getMpUserList(String accessToken,String nextOpenId) {
        return _replaceAccessToken(MP_USER_LIST_URL,accessToken).replace("NEXT_OPENID",nextOpenId!=null?nextOpenId:"");
    }

    /**
     * 获取发送模板消息接口 URL
     * @param accessToken
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:50
     * @return
     */
    public static String getMpMessageTemplateSendUrl(String accessToken) {
        return _replaceAccessToken(MP_MESSAGE_TEMPLATE_SEND_URL,accessToken);
    }

    /**
     * 获取公众号jssdk jsApiTcket接口 URL
     * @param accessToken
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:53
     * @return
     */
    public static String getMpJsSdkTicketUrl(String accessToken) {
        return _replaceAccessToken(MP_JS_SDK_TICKET_URL,accessToken);
    }

    /**
     * 获取网页授权用户信息URL
     * @param accessToken
     * @param openId
     * @author kango2gler@gmail.com
     * @date 2017/6/6 16:25
     * @return
     */
    public static String getOAth2UserInfoUrl(String accessToken, String openId,String lang) {
        return _replaceAccessToken(OAUTH2_USER_INFO_URL,accessToken).replace("OPENID",openId).replace("LANG",lang);
    }


    /*******************************************************************************************/

    /**
     * 替换 accessToken
     * @param _str 需要替换的字符串
     * @param accessToken
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:51
     * @return
     */
    private static String _replaceAccessToken(String _str, String accessToken){
        return _str.replace("ACCESS_TOKEN",accessToken);
    }

    /**
     * 替换 appid 和 appsecret
     * @param _str
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:34
     * @return
     */
    private static String _replaceAppIdAndAppSecret(String _str){
        AppIdSecret defaultAppIdSeceret = getDefaultAppIdSecret();
        return _replaceAppIdAndAppSecret(_str,defaultAppIdSeceret.getAppId(),defaultAppIdSeceret.getAppSecret());
    }
    private static String _replaceAppIdAndAppSecret(String _str,String appId,String appSecret){
        return _replaceAppSecret(_replaceAppId(_str,appId),appSecret);
    }
    /**
     * 替换 appid
     * @param _str
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:34
     * @return
     */
    private static String _replaceAppId(String _str){
        return _replaceAppId(_str,getDefaultAppIdSecret().getAppId());
    }
    private static String _replaceAppId(String _str,String appId){
        return _str.replace("APPID",appId);
    }
    /**
     * 替换 appsecret
     * @param _str
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:34
     * @return
     */
    private static String _replaceAppSecret(String _str){
        return _replaceAppSecret(_str,APP_SECRET);
    }
    private static String _replaceAppSecret(String _str,String appSecret){
        return _str.replace("APPSECRET",appSecret);
    }


    /**
     * 默认APP_ID,从配置文件中获取
     */
    public static String APP_ID = "APP_ID";
    /**
     * 默认APP_SECRET,从配置文件中获取
     */
    public static String APP_SECRET = "APP_SECRET";
    /**
     * 多公众号APP_ID,APP_SECRET配置,从配置文件中获取
     */
    public static String APP_ID_SECRETS = "APP_ID_SECRETS";
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
     * MP_ACCESS_TOKEN_URL,
     * 公众号ACCESS_TOKEN接口请求链接,从配置文件中获取
     */
    public static String MP_ACCESS_TOKEN_URL = "MP_ACCESS_TOKEN_URL";
    /**
     * MP_USER_INFO_URL,
     * 获取用户基本信息(UnionID机制)接口请求链接,从配置文件中获取
     */
    public static String MP_USER_INFO_URL = "MP_USER_INFO_URL";
    /**
     * MP_USER_INFO_BATCH_URL,
     * 批量获取用户基本信息(UnionID机制)接口请求链接,从配置文件中获取
     */
    public static String MP_USER_INFO_BATCH_URL = "MP_USER_INFO_BATCH_URL";
    /**
     * MP_USER_LIST,
     * 获取关注者列表接口请求链接,从配置文件中获取
     * 取消关注,再次关注时,用户还在列表的原来位置
     */
    public static String MP_USER_LIST_URL = "MP_USER_LIST_URL";
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
     * 模版id,格式: key:template_id,key2:template_id2... key为自定义的模版类型key,用于程序中获取template_id;template_id为微信模版Id
     */
    public static String MP_MESSAGE_TEMPLATE_IDS = "MP_MESSAGE_TEMPLATE_IDS";
    /**
     * 模版KeyTemplateId Map
     */
    public static Map<String,String> MP_MESSAGE_TEMPLATE_IDS_MAP = new HashMap<>();
    /**
     * <pre>
     * 多APP_ID,APP_SECRETS配置项Map
     * map key: 配置项中的key
     * map value: 配置项中APP_ID,APP_SECRET组成的对象
     * @see #DEFAULT_APP_ID_SECRET_KEY
     * @see #NULL_APP_ID_SECRET
     * </pre>
     */
    public static LinkedHashMap<String,AppIdSecret> APP_ID_SECRETS_MAP = new LinkedHashMap<>();
    /**
     * 空APP_ID_SECRET对象
     */
    public static AppIdSecret NULL_APP_ID_SECRET = new AppIdSecret();
    /**
     * 多APP_ID,APP_SECRETS配置项Map的默认key
     * @see #APP_ID_SECRETS_MAP
     */
    public static String DEFAULT_APP_ID_SECRET_KEY = "DEFAULT";

    /**
     * 初始化配置信息
     */
    static{
        APP_ID = WeChatConfigHelper.getValue("APP_ID");
        APP_SECRET = WeChatConfigHelper.getValue("APP_SECRET");
        APP_ID_SECRETS = WeChatConfigHelper.getValue("APP_ID_SECRETS");
        OAUTH2_AUTHORIZE_URL = WeChatApiUrlConfigHelper.getValue("OAUTH2_AUTHORIZE_URL");
        OAUTH2_ACCESS_TOKEN_URL = WeChatApiUrlConfigHelper.getValue("OAUTH2_ACCESS_TOKEN_URL");
        OAUTH2_USER_INFO_URL = WeChatApiUrlConfigHelper.getValue("OAUTH2_USER_INFO_URL");
        MP_ACCESS_TOKEN_URL = WeChatApiUrlConfigHelper.getValue("MP_ACCESS_TOKEN_URL");
        MP_USER_INFO_URL = WeChatApiUrlConfigHelper.getValue("MP_USER_INFO_URL");
        MP_USER_INFO_BATCH_URL = WeChatApiUrlConfigHelper.getValue("MP_USER_INFO_BATCH_URL");
        MP_USER_LIST_URL = WeChatApiUrlConfigHelper.getValue("MP_USER_LIST_URL");
        MP_MESSAGE_TEMPLATE_SEND_URL = WeChatApiUrlConfigHelper.getValue("MP_MESSAGE_TEMPLATE_SEND_URL");
        MP_JS_SDK_TICKET_URL = WeChatApiUrlConfigHelper.getValue("MP_JS_SDK_TICKET_URL");
        MP_MESSAGE_TEMPLATE_IDS = WeChatConfigHelper.getValue("MP_MESSAGE_TEMPLATE_IDS");
        //初始化模版消息Ids
        if (StringUtils.isNotBlank(MP_MESSAGE_TEMPLATE_IDS)) {
            stringValueToMap(MP_MESSAGE_TEMPLATE_IDS,MP_MESSAGE_TEMPLATE_IDS_MAP, new Function<String, String>() {
                @Override
                public String apply(String rawValue) {
                    return rawValue;
                }
            });
        }
        _AppIdSecretsMapInit();
        logger.info("initial wx-config: APP_ID:"+APP_ID+"\n"+
                        "APP_SECRET:"+APP_SECRET+"\n"+
                        "APP_ID_SECRETS:"+APP_ID_SECRETS+"\n"+
                        "APP_ID_SECRETS_MAP:"+APP_ID_SECRETS_MAP+"\n"+
                "OAUTH2_AUTHORIZE_URL:"+OAUTH2_AUTHORIZE_URL+"\n"+
                "OAUTH2_ACCESS_TOKEN_URL:"+OAUTH2_ACCESS_TOKEN_URL+"\n"+
                "OAUTH2_USER_INFO_URL:"+OAUTH2_USER_INFO_URL+"\n"+
                "MP_ACCESS_TOKEN_URL:"+MP_ACCESS_TOKEN_URL+"\n"+
                "MP_USER_INFO_URL:"+MP_USER_INFO_URL+"\n"+
                "MP_USER_LIST_URL:"+MP_USER_LIST_URL+"\n"+
                "MP_MESSAGE_TEMPLATE_SEND_URL:"+MP_MESSAGE_TEMPLATE_SEND_URL+"\n"+
                "MP_JS_SDK_TICKET_URL:"+MP_JS_SDK_TICKET_URL+"\n"+
                "MP_MESSAGE_TEMPLATE_IDS:"+MP_MESSAGE_TEMPLATE_IDS+"\n"+
                "MP_MESSAGE_TEMPLATE_IDS_MAP:"+MP_MESSAGE_TEMPLATE_IDS_MAP+"\n"
        );

    }

    /**
     * APP_ID_SECRETS_MAP初始化
     */
    private static void _AppIdSecretsMapInit(){
        //初始化AppIdSecrets
        if (StringUtils.isNotBlank(APP_ID_SECRETS)) {
            //DEFAULT处理,
            if (APP_ID != null) {
                //将APP_ID作为default添加到APP_ID_SECRETS_MAP中第一个位置
                APP_ID_SECRETS_MAP.put(DEFAULT_APP_ID_SECRET_KEY, new AppIdSecret(APP_ID, APP_SECRET));
            }
            final AtomicInteger i = new AtomicInteger();
            stringValueToMap(APP_ID_SECRETS,APP_ID_SECRETS_MAP, new Function<String, AppIdSecret>() {
                @Override
                public AppIdSecret apply(String rawValue) {
                    AppIdSecret appIdSecret = null;
                    if (StringUtils.isNotBlank(rawValue)) {
                        String[] appIdSecrets = rawValue.split("#");
                        appIdSecret = new AppIdSecret(appIdSecrets[0], (appIdSecrets.length > 1 ? appIdSecrets[1] : ""));
                    } else {
                        appIdSecret = new AppIdSecret();
                    }
                    if (i.get() == 0 && !APP_ID_SECRETS_MAP.containsValue(DEFAULT_APP_ID_SECRET_KEY)) {
                        APP_ID_SECRETS_MAP.put(DEFAULT_APP_ID_SECRET_KEY, appIdSecret);
                    }
                    i.getAndIncrement();
                    return appIdSecret;
                }
            });
            if (APP_ID == null) {
                //从APP_ID_SECRETS_MAP中取第一个设置默认APP_ID
                if (APP_ID_SECRETS_MAP.size() > 0) {
                    AppIdSecret defaultAppIdSecret = getDefaultAppIdSecret();
                    APP_ID = defaultAppIdSecret.getAppId();
                    APP_SECRET = defaultAppIdSecret.getAppSecret();
                }
            }
        }
    }

    /**
     * 解析配置项字符串为Map
     * 格式: {key}:{value},{key2}:{value2}
     * @param str 待处理的字符串
     * @param map 解析后的map
     * @param mapValueHandler value值处理
     * @param <T>
     */
    private static <T> void stringValueToMap(String str, Map<String,T> map, Function<String,T> mapValueHandler) {
        StringTokenizer st = new StringTokenizer(str, ",");
        while (st.hasMoreElements()) {
            String keyTemplateId = st.nextToken();
            if (StringUtils.isNotBlank(keyTemplateId)) {
                String[] kt = keyTemplateId.split(":");
                String rawValue = kt.length > 1 ? kt[1] : "";
                T val = mapValueHandler.apply(rawValue);
                map.put(kt[0], val);
            }
        }
    }

    /**
     * 通过key从{@link WeChatConfig#APP_ID_SECRETS_MAP}中获取AppIdSecret
     * @param key
     * @return
     * @see #getDefaultAppIdSecret()
     * @see #_AppIdSecretsMapInit()
     * @see #getAppIdSecret(String)
     */
    public static AppIdSecret getAppIdSecretByKey(String key){
        if (APP_ID_SECRETS_MAP.containsKey(key)) {
            return APP_ID_SECRETS_MAP.get(key);
        }
        return NULL_APP_ID_SECRET;
    }
    /**
     * 通过appId从{@link WeChatConfig#APP_ID_SECRETS_MAP}中获取AppIdSecret
     * @param appId
     * @return
     * @see #getDefaultAppIdSecret()
     * @see #_AppIdSecretsMapInit()
     */
    public static AppIdSecret getAppIdSecret(String appId){
        for (Iterator<AppIdSecret> it = APP_ID_SECRETS_MAP.values().iterator(); it.hasNext();) {
            AppIdSecret appIdSecret= it.next();
            if (appIdSecret.getAppId().equals(appId)) {
                return appIdSecret;
            }
        }
        return NULL_APP_ID_SECRET;
    }
    /**
     * 获取默认的AppIdSecret
     * @return
     * @see #getAppIdSecret(String)
     * @see #_AppIdSecretsMapInit()
     */
    public static AppIdSecret getDefaultAppIdSecret(){
        return getAppIdSecretByKey(DEFAULT_APP_ID_SECRET_KEY);
    }

    /**
     * 消息模版Id帮助类
     */
    public static class MessageTemplateIdHelper{
        /**
         * 通过key获取模版Id
         * @param key
         * @return
         */
        public static String getTemplateIdByKey(String key) {
            return MP_MESSAGE_TEMPLATE_IDS_MAP.get(key);
        }

        /**
         * 获取所有已配置的模版id
         * @return
         */
        public static Collection<String> getTemplateIds(){
            return MP_MESSAGE_TEMPLATE_IDS_MAP.values();
        }

        /**
         * 获取所有key templateId Map
         * @return <key,temlateId></key,temlateId>
         */
        public static Map<String, String> getTemplateIdsMap() {
            return MP_MESSAGE_TEMPLATE_IDS_MAP;
        }
    }
    /**
     * 网页授权 作用域
     * @author kango2gler@gmail.com
     * @date 2017/2/13 16:37
     */
    public enum OAth2Scope{
        /**
         * snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
         * @author kango2gler@gmail.com
         * @date 2017/2/13 16:38
         */
        SNSAPI_BASE{
            @Override
            public String toString() {
                return super.toString().toLowerCase();
            }
        },
        /**
         * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
         * @author kango2gler@gmail.com
         * @date 2017/2/13 16:38
         */
        SNSAPI_USERINFO{
            @Override
            public String toString() {
                return super.toString().toLowerCase();
            }
        }
    }

    /**
     * AppId,AppSecret组合对象
     */
    public static class AppIdSecret{
        private String appId;
        private String appSecret;

        public AppIdSecret() {
        }

        public AppIdSecret(String appId, String appSecret) {
            this.appId = appId;
            this.appSecret = appSecret;
        }
        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        @Override
        public String toString() {
            return "AppIdSecret{" +
                    "appId='" + appId + '\'' +
                    ", appSecret='" + appSecret + '\'' +
                    '}';
        }
    }
}
