package org.kangspace.wechat.endpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kangspace.wechat.cache.WeChatJsApiTicketCache;
import org.kangspace.wechat.mp.JSApiTicketSignBean;
import org.kangspace.wechat.oauth2.OAuth2InterfaceAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/12/7 10:58
 */
@RunWith(JUnit4.class)
public class WeChatControllerTest {
    private WeChatController weChatController = new WeChatController(new WeChatJsApiTicketCache(),new OAuth2InterfaceAccess());

    @Test
    public void authTest() {
        HttpServletRequest request = null;
        HttpServletResponse response= null;
        String appId = "";
        String state = "";
        String callbackUrl = "";
        weChatController.auth(request,response,appId,state,callbackUrl);
    }
    @Test
    public void authCallbackTest() {
        HttpServletRequest request = null;
        HttpServletResponse response= null;
        String code = "";
        String state = "";
        String callbackPageUrl = "";
        weChatController.authCallback(request,response,code,state,callbackPageUrl);
    }
    @Test
    public void getWXJSSDKSignTest() {
        String appId = "";
        String url = "";
        JSApiTicketSignBean bean = weChatController.getWXJSSDKSign(appId,url);
        System.out.println(bean);
    }
}
