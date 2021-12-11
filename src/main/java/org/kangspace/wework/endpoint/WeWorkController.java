package org.kangspace.wework.endpoint;

import org.apache.commons.lang.StringUtils;
import org.kangspace.wechat.util.WebUtil;
import org.kangspace.wechat.util.encryption.Sha1Util;
import org.kangspace.wework.WeWorkInterfaceAccess;
import org.kangspace.wework.cache.WeWorkAppJsApiTicketCache;
import org.kangspace.wework.cache.WeWorkJsApiTicketCache;
import org.kangspace.wework.jssdk.JSApiTicketSignBean;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * <pre>
 * 企业相关Controller
 * /api/wechat
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2021/11/24 23:30:30
 */
public class WeWorkController {
    private org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private WeWorkJsApiTicketCache weWorkJsApiTicketCache;
    private WeWorkAppJsApiTicketCache weWorkAppJsApiTicketCache;
    private WeWorkInterfaceAccess weWorkInterfaceAccess;

    public WeWorkJsApiTicketCache getWeWorkJsApiTicketCache() {
        return weWorkJsApiTicketCache;
    }

    public void setWeWorkJsApiTicketCache(WeWorkJsApiTicketCache weWorkJsApiTicketCache) {
        this.weWorkJsApiTicketCache = weWorkJsApiTicketCache;
    }

    public WeWorkAppJsApiTicketCache getWeWorkAppJsApiTicketCache() {
        return weWorkAppJsApiTicketCache;
    }

    public void setWeWorkAppJsApiTicketCache(WeWorkAppJsApiTicketCache weWorkAppJsApiTicketCache) {
        this.weWorkAppJsApiTicketCache = weWorkAppJsApiTicketCache;
    }

    public WeWorkInterfaceAccess getWeWorkInterfaceAccess() {
        return weWorkInterfaceAccess;
    }

    public void setWeWorkInterfaceAccess(WeWorkInterfaceAccess weWorkInterfaceAccess) {
        this.weWorkInterfaceAccess = weWorkInterfaceAccess;
    }

    public WeWorkController(WeWorkJsApiTicketCache weWorkJsApiTicketCache,
                            WeWorkAppJsApiTicketCache weWorkAppJsApiTicketCache,
                            WeWorkInterfaceAccess weWorkInterfaceAccess) {
        this.weWorkJsApiTicketCache = weWorkJsApiTicketCache;
        this.weWorkAppJsApiTicketCache = weWorkAppJsApiTicketCache;
        this.weWorkInterfaceAccess = weWorkInterfaceAccess;
    }

    public WeWorkController() {
    }

    /**
     * <pre>
     * 企业微信网页授权访问入口
     * auth/{appId}
     * </pre>
     * @param appId
     * @param state
     * @author kango2gler@gmail.com
     * @since 2021/11/24 23:30:30
     * @return
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
            weWorkInterfaceAccess.redirectToAuthorizeUrl(appId,url,state,request,response);
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
     * @return
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
        String openId = weWorkInterfaceAccess.getUserId(appId,code);
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
     * 获取企业微信企业jssdk签名信息
     * jssign/{appId}
     * </pre>
     * @param appId
     * @param url
     * @author kango2gler@gmail.com
     * @since 2016/11/15 17:17
     * @return
     */
    public JSApiTicketSignBean getWXJSSDKSign(String appId,String url){
        String jsApiTicket = weWorkJsApiTicketCache.getValByAppId(appId);
        JSApiTicketSignBean bean = new JSApiTicketSignBean();
        bean.setAppId(appId);
        bean.setUrl(url);
        bean.setNonceStr(Sha1Util.getNonceStr());
        bean.setJsApiTicket(jsApiTicket);
        bean.setTimestamp(System.currentTimeMillis()+"");
        try {
            bean = WeWorkInterfaceAccess.getJsApiSign(bean);
        } catch (Exception e) {
            log.error("获取企业jssdk签名信息失败:"+e.getMessage(),e);
        }
        return bean;
    }

    /**
     * <pre>
     * 获取企业微信应用jssdk签名信息
     * jssign/{appId}
     * </pre>
     * @param appId
     * @param url
     * @author kango2gler@gmail.com
     * @since 2016/11/15 17:17
     * @return
     */
    public JSApiTicketSignBean getWXAppJSSDKSign(String appId, String agentId, String url){
        String jsApiTicket = weWorkAppJsApiTicketCache.getValByAppId(appId);
        JSApiTicketSignBean bean = new JSApiTicketSignBean();
        bean.setAppId(appId);
        bean.setAgentId(agentId);
        bean.setUrl(url);
        bean.setNonceStr(Sha1Util.getNonceStr());
        bean.setJsApiTicket(jsApiTicket);
        bean.setTimestamp(System.currentTimeMillis()+"");
        try {
            bean = WeWorkInterfaceAccess.getJsApiSign(bean);
        } catch (Exception e) {
            log.error("获取应用jssdk签名信息失败:"+e.getMessage(),e);
        }
        return bean;
    }
}
