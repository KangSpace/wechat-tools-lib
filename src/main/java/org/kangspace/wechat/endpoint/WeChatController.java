package org.kangspace.wechat.endpoint;

import org.apache.commons.lang.StringUtils;
import org.kangspace.wechat.cache.WeChatJsApiTicketCache;
import org.kangspace.wechat.mp.JSApiTicketSignBean;
import org.kangspace.wechat.mp.MpInterfaceAccess;
import org.kangspace.wechat.oauth2.OAuth2InterfaceAccess;
import org.kangspace.wechat.util.WebUtil;
import org.kangspace.wechat.util.encryption.Sha1Util;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * <pre>
 * 微信相关Controller
 * /api/wechat
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/11/30 16:34
 */
public class WeChatController {
    private org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private WeChatJsApiTicketCache wxJsApiTicketCache;
    private OAuth2InterfaceAccess oAth2InterfaceAccess;

    public WeChatJsApiTicketCache getWxJsApiTicketCache() {
        return wxJsApiTicketCache;
    }

    public void setWxJsApiTicketCache(WeChatJsApiTicketCache wxJsApiTicketCache) {
        this.wxJsApiTicketCache = wxJsApiTicketCache;
    }

    public OAuth2InterfaceAccess getoAth2InterfaceAccess() {
        return oAth2InterfaceAccess;
    }

    public void setoAth2InterfaceAccess(OAuth2InterfaceAccess oAth2InterfaceAccess) {
        this.oAth2InterfaceAccess = oAth2InterfaceAccess;
    }

    public WeChatController(WeChatJsApiTicketCache wxJsApiTicketCache, OAuth2InterfaceAccess oAth2InterfaceAccess) {
        this.wxJsApiTicketCache = wxJsApiTicketCache;
        this.oAth2InterfaceAccess = oAth2InterfaceAccess;
    }

    public WeChatController() {
    }

    /**
     * <pre>
     * 微信网页授权访问入口
     * auth/{appId}
     * </pre>
     * @param request request
     * @param response response
     * @param appId appId
     * @param state state
     * @param callbackUrl callbackUrl
     * @author kango2gler@gmail.com
     * @since 2020-11-30 17:43:13
     */
    public void auth(HttpServletRequest request,
                     HttpServletResponse response,
                     String appId,
                     String state,
                     String callbackUrl){

        //通过网页授权回调地址
        String url = callbackUrl;
        if (StringUtils.isEmpty(url)) {
            WebUtil.write(("appId:" + appId + ",回调地址不存在"),response);
            return;
        }
        //跳转到微信授权页面
        try {
            state = appId + (StringUtils.isNotEmpty(state)?","+state:"");
            oAth2InterfaceAccess.redirectToAuthorizeUrl(appId,url,state,request,response);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
            WebUtil.write(("appId:" + appId + ",跳转微信授权失败,ex:"+e.getMessage()),response);
        }
    }
    /**
     * <pre>
     * 网页授权通过后,通过CODE获取OpenId,重定向前端页面
     * auth-callback
     * </pre>
     * @param code
     * @param state 第一个,前的字符串为appId
     * @author kango2gler@gmail.com
     * @since 2020-11-30 17:43:13
     */
    public void authCallback(HttpServletRequest request,
                             HttpServletResponse response,
                             String code,
                             String state,
                             String callbackPageUrl){
        //解析state,取第一个,前的数据为appId
        int firstSplitIdx = state.indexOf(",");
        String appId = "";
        if (firstSplitIdx == -1) {
            appId = state;
            state = "";
        }else{
            appId = state.substring(0, firstSplitIdx);
            state = state.substring(firstSplitIdx + 1);
        }
        //重定向地址
        String url = callbackPageUrl;
        //获取openId
        String openId = oAth2InterfaceAccess.getOpenId(appId,code);
        if (StringUtils.isEmpty(openId)) {
            WebUtil.write("appId:" + appId + ",获取微信网页授权失败",response);
            return;
        }
        //重新向到H5
        url += (url.indexOf("?") > 0 ? "&" : "?") + "openid=" + openId +(state.length()>0?"&state=" + state:"");
        WebUtil.redirectRequest(url,request,response);
    }

    /**
     * <pre>
     * 获取微信jssdk签名信息
     * jssign/{appId}
     * </pre>
     * @param appId appId
     * @param url url
     * @author kango2gler@gmail.com
     * @since 2016/11/15 17:17
     * @return
     */
    public JSApiTicketSignBean getWXJSSDKSign(String appId,String url){
        String jsApiTicket = wxJsApiTicketCache.getValByAppId(appId);
        JSApiTicketSignBean bean = new JSApiTicketSignBean();
        bean.setAppId(appId);
        bean.setUrl(url);
        bean.setNonceStr(Sha1Util.getNonceStr());
        bean.setJsApiTicket(jsApiTicket);
        bean.setTimestamp(System.currentTimeMillis()+"");
        try {
            bean = MpInterfaceAccess.getJsApiSign(bean);
        } catch (Exception e) {
            log.error("获取jssdk签名信息失败:"+e.getMessage(),e);
        }
        return bean;
    }
}
