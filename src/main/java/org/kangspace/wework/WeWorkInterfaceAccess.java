package org.kangspace.wework;

import org.kangspace.wechat.bean.WeChatCapableRetrieveToken;
import org.kangspace.wechat.bean.WeChatReturnBean;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.mp.AccessTokenReturnBean;
import org.kangspace.wechat.util.WeChatUtil;
import org.kangspace.wechat.util.WebUtil;
import org.kangspace.wechat.util.encryption.Sha1Util;
import org.kangspace.wechat.util.http.MyAbstractHttp;
import org.kangspace.wechat.util.http.MyHttpUtil;
import org.kangspace.wework.jssdk.JSApiTicketReturnBean;
import org.kangspace.wework.jssdk.JSApiTicketSignBean;
import org.kangspace.wework.user.UserInfoReturnBean;
import org.kangspace.wework.mini.Code2SessionReturnBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 公众号接口访问
 * @date 2021/08/28 16:17:20
 */
public class WeWorkInterfaceAccess extends WeChatCapableRetrieveToken {
    private static Logger logger = Logger.getLogger(WeWorkInterfaceAccess.class.getName());

    /**
     * 302重定向跳转到网页授权
     * @param redirectURI 网页授权回调地址
     * @param param 网页授权state参数设置
     * @param request
     * @param response
     * @author kango2gler@gmail.com
     * @date 2017/2/15 17:24
     * @return
     */
    public void redirectToAuthorizeUrl(String redirectURI, String param, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        redirectToAuthorizeUrl(WeChatConfig.getDefaultAppIdSecret().getAppId(),redirectURI,param, WeChatConfig.OAth2Scope.SNSAPI_BASE,request,response);
    }

    /**
     * 302重定向跳转到网页授权
     * @param appId 微信APP_ID
     * @param redirectURI 网页授权回调地址
     * @param param 网页授权state参数设置
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    public void redirectToAuthorizeUrl(String appId, String redirectURI, String param, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        redirectToAuthorizeUrl(appId,redirectURI,param, WeChatConfig.OAth2Scope.SNSAPI_BASE,request,response);
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
     * @author kango2gler@gmail.com
     * @date 2017/2/15 17:24
     */
    public void redirectToAuthorizeUrl(String appId,String redirectURI, String param, WeChatConfig.OAth2Scope scope, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String url = WeChatConfig.getOauth2AuthorizeUrl(appId,redirectURI, scope, (param != null ? URLEncoder.encode(param, "UTF-8") : ""));
        WebUtil.redirectRequest(url, request, response);
    }

    /**
     * 获取公众号AccessToken
     * 该参数需缓存
     * @author kango2gler@gmail.com
     * @date 2021/08/28 16:17:20
     * @return
     * @see #getAccessToken(String)
     */
    public AccessTokenReturnBean getAccessToken(){
        return getAccessToken(WeChatConfig.getDefaultAppIdSecret().getAppId());
    }
    /**
     * 获取公众号AccessToken
     * 该参数需缓存
     * @author kango2gler@gmail.com
     * @date 2021/08/28 16:17:20
     * @return
     * @see #getAccessToken()
     */
    public AccessTokenReturnBean getAccessToken(String appId){
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkAccessTokenUrl(appId));
        AccessTokenReturnBean rb = WeChatUtil.asReturnBean(client.get(),AccessTokenReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 通过code获取openId
     * @param code
     * @return
     */
    public String getUserId(String appId,String code) {
        UserInfoReturnBean bean = getUserInfo(appId,code);
        if (WeChatReturnBean.isSuccess(bean)) {
            return bean.getUserId();
        }
        return null;
    }

    /**
     * 通过code获取用户信息
     * @param code
     * @return
     */
    public UserInfoReturnBean getUserInfo(String appId,String code) {
        AccessTokenReturnBean bean = getAccessToken(appId);
        if (WeChatReturnBean.isSuccess(bean)) {
            return getUserInfoByToken(bean.getAccessToken(), code);
        }else{
            logger.severe("获取网页授权AccessToken失败,error:"+bean);
        }
        return null;
    }
    /**
     * 通过code获取openId
     * @param code
     * @return
     */
    public UserInfoReturnBean getUserInfoByToken(String accessToken,String code) {
        UserInfoReturnBean bean = userGetUserInfo(accessToken, code);
        if (WeChatReturnBean.isSuccess(bean)) {
            return bean;
        } else {
            logger.severe("通过code获取企业微信用户信息失败,error:" + bean);
        }
        return null;
    }

    /**
     * 企业微信通过小程序JsCode临时登录凭证校验
     * @param accessToken
     * @param code
     */
    public Code2SessionReturnBean code2Session(String accessToken,String code) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkMiniProgramCode2SessionUrl(accessToken,code));
        Code2SessionReturnBean rb = WeChatUtil.asReturnBean(client.get(),Code2SessionReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 获取访问用户身份(根据code获取成员信息)
     * @param accessToken
     * @param code
     */
    public UserInfoReturnBean userGetUserInfo(String accessToken, String code) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkUserGetUserInfoUrl(accessToken,code));
        UserInfoReturnBean rb = WeChatUtil.asReturnBean(client.get(),UserInfoReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 获取企业的jsapi_ticket
     * @param accessToken
     */
    public JSApiTicketReturnBean getJsApiTicket(String accessToken) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkJsSdkTicketUrl(accessToken));
        JSApiTicketReturnBean rb = WeChatUtil.asReturnBean(client.get(),JSApiTicketReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 获取应用的jsapi_ticket
     * @param accessToken
     * @return JSApiTicketReturnBean
     */
    public JSApiTicketReturnBean getAgentConfigTicket(String accessToken) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkAppJsSdkTicketUrl(accessToken));
        JSApiTicketReturnBean rb = WeChatUtil.asReturnBean(client.get(),JSApiTicketReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * <pre>
     * JsApi权限验证获取sign
     * 获得jsapi_ticket之后，就可以生成JS-SDK权限验证的签名了。
     签名算法
     签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
     即signature=sha1(string1)。 示例：
     noncestr=Wm3WZYTPz0wzccnW
     jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
     timestamp=1414587457
     url=http://mp.weixin.qq.com?params=value
     步骤1. 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1：
     jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
     步骤2. 对string1进行sha1签名，得到signature：
     0f9de62fce790f9a083d5c99e95740ceb90c27ed

     String jsApiTicket,String url,String noncestr,Long timestamp
     其中的Url需encodeURIComponent
     </pre>
     * @param
     * @author kango2gler@gmail.com
     * @date 2017/1/8 23:48
     * @return
     */
    public static JSApiTicketSignBean getJsApiSign(JSApiTicketSignBean signBean) throws Exception {
        String url = signBean.getUrl();
        if (url.contains("#")) {
            url = url.substring(0, url.indexOf("#"));
        }
        //签名
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("jsapi_ticket", signBean.getJsApiTicket());
        signParams.put("timestamp", signBean.getTimestamp());
        signParams.put("noncestr", signBean.getNonceStr());
        signParams.put("url", url);
        String sign = Sha1Util.createSHA1Sign(signParams);
        signBean.setSignature(sign);
        return signBean;
    }
}
